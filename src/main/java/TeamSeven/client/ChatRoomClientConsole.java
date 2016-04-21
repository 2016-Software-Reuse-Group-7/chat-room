package TeamSeven.client;

import TeamSeven.client.socket.ChatRoomClientSocket;
import TeamSeven.client.socket.ChatRoomClientSocketImpl;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.dispatcher.ConsoleClientSideMessageDispatcher;
import TeamSeven.dispatcher.MessageDispatcher;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Key;
import java.security.PublicKey;

/**
 * Created by joshoy on 16/4/17.
 */
public class ChatRoomClientConsole {

    /* Server URI, TODO: 从配置文件中读取 */
    URI serverUri;
    /* Client WebSocket */
    protected ChatRoomClientSocket clientSocket;
    /* 连接加密方式, 初始为null */
    EncryptTypeEnum connectionEncryptType;
    /* 与Server连接的公钥 */
    protected PublicKey pubKey;
    /* dispatcher */
    MessageDispatcher dispatcher;

    public ChatRoomClientConsole() {
        try {
            this.serverUri = new URI("ws://127.0.0.1:8077");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.dispatcher = new ConsoleClientSideMessageDispatcher(this);
        this.connectionEncryptType = null;
    }

    /* *
     *  +------------------+
     *  | API for handlers |
     *  +------------------+
     * */

    /**
     * 设置连接服务器使用的公钥
     * @param pubKey
     */
    public void setServerPublicKey(Key pubKey) {
        this.pubKey = (PublicKey) pubKey;
    }

    /**
     * 连接至对应URI的Server
     * @param uri
     */
    public void startConnection(URI uri) {
        this.clientSocket = new ChatRoomClientSocketImpl(uri, this.dispatcher);
    }

    /**
     * 设置加密方式, null表示不加密
     * @param type
     */
    public void setEncryptType(EncryptTypeEnum type) {
        this.connectionEncryptType = type;
    }

    /**
     * 发送数据流 (String)
     * @param data
     */
    public void sendData(String data) {
        this.clientSocket.send(data);
    }

    /**
     * 发送数据流 (byte[])
     * @param data
     */
    public void sendData(byte[] data) {
        this.clientSocket.send(data);
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        if (null != this.clientSocket) {
            this.clientSocket.close();
            this.clientSocket = null;
        }
    }

}
