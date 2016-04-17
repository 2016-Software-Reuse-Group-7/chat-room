# chat-room
New version of chat room after refactor.

## 开发注意事项

** 请使用IntelliJ Idea进行开发, 请使用IntelliJ Idea进行开发, 请使用IntelliJ Idea进行开发.  **
IntelliJ Idea 下载地址戳<a href="https://www.jetbrains.com/idea/">这里</a>.

运行环境: JDK 1.8

从git上clone本项目, 
```
git clone https://github.com/2016-Software-Reuse-Group-7/chat-room.git && cd chat-room
```
然后使用Idea打开.

## 目录结构

```
- client包: 客户端的相关代码
- server包: 服务端的相关代码
- common包: 枚举（如消息类型）等相关类代码
- entry包: 运行入口
- database包: 数据库访问包
- util包: 各类工具类
  - config包: 配置管理
  - encrypt包: 消息加密
  - performace包: 性能管理
  - serialize包: 序列化
  - ...(如有其他功能请添加)
```

### 关于为什么要将接口和实现分离

为了遵循<a href="https://en.wikipedia.org/wiki/Interface_segregation_principle">接口分离原则</a>, 降低耦合性.
