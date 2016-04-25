package TeamSeven.client;

import TeamSeven.client.socket.ChatRoomClientSocket;
import TeamSeven.client.socket.ChatRoomClientSocketImpl;
import TeamSeven.common.entity.Account;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.message.BaseMessage;
import TeamSeven.dispatcher.ConsoleClientSideMessageDispatcher;
import TeamSeven.dispatcher.MessageDispatcher;
import TeamSeven.util.encrypt.AsymmertricCoder;
import TeamSeven.util.encrypt.SymmetricCoder;
import TeamSeven.util.serialize.ChatRoomSerializer;
import TeamSeven.util.serialize.ChatRoomSerializerImpl;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;

/**
 * Created by joshoy on 16/4/17.
 */
public class ChatRoomClientConsole {

    /* Server URI, TODO: 从配置文件中读取 */
    URI serverUri;
    /* 序列化工具 */
    ChatRoomSerializer serializeTool;
    /* Client WebSocket */
    protected ChatRoomClientSocket clientSocket;
    /* 连接加密方式, 初始为null */
    EncryptTypeEnum connectionEncryptType;
    /* 与Server连接的公钥, 由Server提供 */
    protected PublicKey pubKey;
    /* 与Server连接的密钥 */
    protected SecretKey secKey;
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
        this.serializeTool = new ChatRoomSerializerImpl();
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
        this.clientSocket = new ChatRoomClientSocketImpl(uri, this.dispatcher, this.connectionEncryptType);
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
    public void sendRaw(String data) {
        this.clientSocket.send(data);
    }

    /**
     * 发送数据流 (byte[])
     * @param data
     */
    public void sendRaw(byte[] data) {
        this.clientSocket.send(data);
    }

    /**
     * 向服务端发送序列化 + 加密后的消息 (如不加密, 则只序列化)
     * @param msg
     */
    public void sendMessageWithEncryption(BaseMessage msg) {
        try {
            String serializedMessage = this.serializeTool.serializeObjectAndStringify(msg);
            String sendingBuffer = null;
            if (this.connectionEncryptType != null) {
                if (this.connectionEncryptType.isSymmetricEncryption()) {
                    SymmetricCoder sc = (SymmetricCoder) this.connectionEncryptType.getCoderClass().newInstance();
                    String encryptedBuffer = new String(sc.encrypt(serializedMessage, this.secKey));
                    sendingBuffer = this.serializeTool.serializeObjectAndStringify(encryptedBuffer);
                }
                else {
                    AsymmertricCoder ac = (AsymmertricCoder) this.connectionEncryptType.getCoderClass().newInstance();
                    ac.setPublicKey(this.pubKey);
                    String encryptedBuffer = new String(ac.encryptWithPublicKey(serializedMessage.getBytes()));
                    sendingBuffer = this.serializeTool.serializeObjectAndStringify(encryptedBuffer);
                }
            }
            else {
                sendingBuffer = serializedMessage;
            }
            /* 无误后, 发送 */
            this.sendRaw(sendingBuffer);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("发送序列化加密消息失败.");
        }
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

    /**
     * 从配置文件中读取加密类型
     * @return
     */
    public EncryptTypeEnum readEncryptionTypeFromConfig() {
        // TODO: 从配置文件中读取加密类型
        return EncryptTypeEnum.AES;
    }

    /**
     * 向自身dispatch一个消息
     */
    public void selfDispatch() {

    }

    public void setConnectionEncryptKey(Key k) {
        this.clientSocket.setConnectionKey(k);
    }
}
