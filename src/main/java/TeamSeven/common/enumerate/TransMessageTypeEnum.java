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
    CLIENT_ACT_START_CONNECTION,
    CLIENT_RESP_ENCRYPT_TYPE,
    CLIENT_GROUP_CHAT,
    // 服务端产生的消息类型
    SERVER_ACK,
    SERVER_BOARDCAST,
    SERVER_SECKEY,
    SERVER_PUBKEY,
    SERVER_ASK_ENCRYPT_TYPE,
    SERVER_ASK_LOGIN,
    SERVER_RESP_LOGIN_SUCCESS,
    SERVER_RESP_LOGIN_FAILED,
    SERVER_TRANSFER_CHAT,
    SERVER_RECEIVE,
    SERVER_GROUP_CHAT,
    SERVER_DELAY_CHAT,
    LOGIN_STATUS,
    // TODO: add more
    REQ_LICENSE_SERVER_USER_LIMITATION,
}
