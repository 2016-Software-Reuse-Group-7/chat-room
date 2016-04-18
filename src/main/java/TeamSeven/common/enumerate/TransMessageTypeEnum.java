package TeamSeven.common.enumerate;

/**
 * Created by joshoy on 16/4/17.
 */
public enum TransMessageTypeEnum {
    // 客户端产生的消息类型
    CLIENT_HEARTBEAT,
    CLIENT_LOGIN,
    CLIENT_ACK,
    CLIENT_CHAT,
    CLIENT_LOGOUT,
    CLIENT_SECKEY,
    CLIENT_PUBKEY,
    // 服务端产生的消息类型
    SERVER_BOARDCAST,
    SERVER_SECKEY,
    SERVER_PUBKEY,
    // TODO: add more
}
