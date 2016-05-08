# chat-room
New version of chat room after refactor.



##组员

| 姓名      | 学号      | Github账号          |
| :-------: | :-------: | :-----------------: |
| 欧阳俊鹏   | 1352847   | [JoshOY][1]       |
| 侯潇雪    | 1352855   | [4majesty][2]     |
| 赵明阳    | 1352989   | [DDMing][3]     |
| 林悦锵      | 1352888   | [linnnnnnnnnn][4]      |
| 陈宏俊      | 1352916   | [AndyCHJ][5] |


## 开发注意事项

请使用IntelliJ Idea进行开发, 请使用IntelliJ Idea进行开发, 请使用IntelliJ Idea进行开发.

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
- common包: 枚举, 消息类型, 实体等相关类代码
- entry包: 运行入口
- dispatcher包: Dispatcher类, 具体见架构设计文档.
- handler包: Handler类, 具体见架构设计文档.
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

### 架构设计

请参见<a href="./doc/项目架构.md">架构设计文档</a>.

### 可复用模块

- 配置管理
- 消息加密
- 性能管理
- 序列化
- TODO: 架构可复用

### 第二次讨论课内容
- [欧阳俊鹏 未补充]()
- [侯潇雪 未补充]()
- [林悦锵 未补充]()
- [赵明阳](https://github.com/DDMing/SoftwareReuseDiscuss/blob/master/%E7%AC%AC%E4%BA%8C%E6%AC%A1%E8%AE%A8%E8%AE%BA%E8%AF%BE%E5%86%85%E5%AE%B9.md)
- [陈宏俊 未补充]()


[1]: https://github.com/JoshOY
[2]: https://github.com/4majesty
[3]: https://github.com/DDMing
[4]: https://github.com/linnnnnnnnnn
[5]: https://github.com/AndyCHJ
