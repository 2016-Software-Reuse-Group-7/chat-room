# 需求

将一个完整的Server分拆成如下几个Server:
 1. 鉴权Server
 2. 消息接收Server
 3. 消息存储Server
 4. 消息转发Server

# 思路

就是把一个Server换成多个Server, Client只和消息接收Server交互, 消息接收Server中的License, 存储, 转发功能需要提取出来, 单独做成另外三个Server,
然后每个Server之间仍然通过Message的方式进行交互. 如下图:

![](./assets/decompose.png)


