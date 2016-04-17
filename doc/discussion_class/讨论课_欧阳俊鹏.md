# 复用解决方案

## 问题
用户在登录聊天室后，保持始终在线，考虑到低带宽、不稳定网络的情况下，如何保持：
- 长连接心跳机制
- 消息不遗漏
- 消息不重复
- 消息压缩

## 本人提出的解决方案

我们在项目中使用的是WebSocket协议，该协议包含一个握手和一个基本消息分帧、分层通过TCP。该协议的提出是为了解决浏览器和Server之间进行全双工通讯的问题。

###  WebSocket的“帧”很小

WebSocket的实现使得服务器与客户端之间交换的数据包档头很小，大概只有2字节。
例如Client端的Request Header：
```
GET /chat HTTP/1.1
Host: server.example.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Origin: http://example.com
Sec-WebSocket-Protocol: chat, superchat
Sec-WebSocket-Version: 13
```
服务端回应的握手信息格式demo如下：

```
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
Sec-WebSocket-Protocol: chat
```

WebSocket 协议的设计理念就是提供极小的帧结构（帧结构存在的目的就是使得协议是基于帧的，而不是基于流的，同时帧可以区分 Unicode 文本和二进制的数据）。

另外，可以通过Base64编码，加快在MIME / HTTP下的传输速度。


### 心跳机制：PING/PONG

WebSocket协议中实现了一个叫 PING/PONG 的消息机制，来使得连接长期存在（keep-alive），进而实现类似于心跳的效果。这种消息甚至能透过代理进行传输。


查阅了相关文档，原理是服务端将一段magic string（可配置）经过encode之后，每个若干秒发送一次给客户端。如果客户端在一段时间内不回复，服务端则将连接断开。

然而遗憾的是，这种PING/PONG的message都由服务端发出。所以并不能做到客户端发心跳包。
但是，我们可以使用替代方案：**客户端每个一段时间发送某种消息类型给服务端，服务端进行确认后，返回对应的消息。** 这样就实现了一种类似于客户端心跳的效果。

### 消息不遗漏

消息遗漏往往出现在带宽低或者连接中断等情况。可能会造成Client端重连过后，消息收不到或者遗漏。其核心问题就是连接的**可靠性**。
很遗憾，**WebSocket在这方面并没有封装解决遗漏的机制。**

但是我们可以参考一些现有的Socket解决方案。也就是对每个Message，Client要进行确认（ACK），也就是向服务端返回一个特殊的Message。
（有点类似于HTTP中的Response）

因此，服务端需要维护的是一个消息队列（MQ）。这种实现机制有点类似于我们使用的消息队列中间件，例如RabbitMQ。

比如说，客户端在consume了一条message之后，需要发送一个ack message，服务端在确认收到ack之后，才将消息从队列中删除（踢出队列）。否则，下次客户端再次请求时，服务端会将已发出，但是未ACK的消息再次发送，直至被ack为止。同理，客户端发送消息后也应该收到服务端的ack.。

### 消息不重复

紧接着上一节的内容，发送方方面，我们虽然实现了较为稳妥的不遗漏机制，但是如果ack包丢失，发送方可能会重发包，这样我们的接收方可能会获得重复的消息。

如何解决呢？参考Socket中常用的方案，我们可以对每个message加一个token。产生这个token的方法有很多，比如基于消息产生的timestamp，内容hashcode等等。

然后，接下来要做的就是在接收方维护一个集合，对已收到的消息token进行检验去重。但是这样会造成消耗大量的内存空间。

另外一个解决方案就是参考类似于TCP的滑窗协议，构造一个递增序列。每个消息都满足后发的消息大于前发的包，并且包的序列号是递增的。再出现异常后，就应该选择丢弃重复的包。

### 消息压缩

比较成熟的方案是使用gzip压缩。
