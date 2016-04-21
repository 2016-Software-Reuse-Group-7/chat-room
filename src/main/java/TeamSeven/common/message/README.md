# 消息类型

本package包含两个包: client和server, 分别代表client和server可能产生的消息类型.

注意, 所有消息都满足继承BaseMessage这个基类. 并且对于每种消息, 调用getType方法都会返回一个enum类型值.

## Client Message 解释

### <a href="./client/ClientChatMessage.java">ClientChatMessage</a>

客户端发送的聊天信息类, 如果初始化时含targetUserId则为私聊消息.

### <a href="./client/ClientLoginMessage.java">ClientLoginMessage</a>

客户端发送的登录消息, Account为登录的账户信息, <del>EncryptTypeEnum为加密类型</del>.

### <a href="./client/ClientLogoutMessage.java">ClientLogoutMessage</a>

客户端发送的登出消息.

