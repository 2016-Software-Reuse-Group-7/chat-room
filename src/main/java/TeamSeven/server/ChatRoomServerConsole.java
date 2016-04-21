package TeamSeven.server;

import TeamSeven.common.entity.Account;
import TeamSeven.common.entity.Session;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.message.BaseMessage;
import TeamSeven.dispatcher.ConsoleServerSideMessageDispatcher;
import TeamSeven.server.account.AccountManager;
import TeamSeven.server.account.AccountManagerImpl;
import TeamSeven.server.session.SessionManager;
import TeamSeven.server.session.SessionManagerImpl;
import TeamSeven.server.socket.ChatRoomServerSocket;
import TeamSeven.server.socket.ChatRoomServerSocketImpl;
import TeamSeven.util.encrypt.AES.AESCoder;
import TeamSeven.util.encrypt.AsymmertricCoder;
import TeamSeven.util.encrypt.RSA.RSACoder;
import TeamSeven.util.encrypt.SymmetricCoder;
import TeamSeven.util.serialize.ChatRoomSerializer;
import TeamSeven.util.serialize.ChatRoomSerializerImpl;
import org.java_websocket.WebSocket;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.*;

/**
 * Created by joshoy on 16/4/17.
 */
public class ChatRoomServerConsole {

    protected final int port = 8077;
    /*
    * 默认公钥/私钥
    * */
    protected PublicKey defaultPublicKey;
    protected PrivateKey defaultPrivateKey;

    /**
     * 设置选项: 是否加密
     */
    protected final boolean encryptMessage = true;
    /*
    *  加密Tool
    * */
    protected SymmetricCoder symmetricCoder = null;
    protected AsymmertricCoder asymmertricCoder = null;
    /*
    * 账号管理
    * */
    protected AccountManager accountManager = null;
    /*
    * Session管理
    * */
    protected SessionManager sessionManager = null;

    /**
     * 序列化工具
     */
    protected ChatRoomSerializer serializeTool;

    protected ConsoleServerSideMessageDispatcher dispatcher;
    protected ChatRoomServerSocket ss;

    public ChatRoomServerConsole() {
        // 创建一个Console Dispatcher
        // 将this作为applier参数传入后, 我们在自定义的handler里就可以直接调用本类提供的方法
        this.serializeTool = new ChatRoomSerializerImpl();
        this.dispatcher = new ConsoleServerSideMessageDispatcher(this);
        this.accountManager = new AccountManagerImpl();
        this.sessionManager = new SessionManagerImpl();

        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            this.defaultPublicKey = keyPair.getPublic();
            this.defaultPrivateKey = keyPair.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // TODO: 根据配置信息选择加密方式
        try {
            this.symmetricCoder = new AESCoder();
            this.asymmertricCoder = new RSACoder();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        // 创建Server WebSocket
        try {
            ss = new ChatRoomServerSocketImpl(port, this.dispatcher, this.sessionManager);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /* *
     *  +------------------+
     *  | API for handlers |
     *  +------------------+
     * */

    /**
     * API: 在console上打印文字
     * @param line: String line
     */
    public void consoleAppendLine(String prefix, String line) {
        System.out.println("[" + prefix + "] " + line);
    }

    /**
     * 发送数据
     * @param conn
     * @param buffer
     */
    public void sendRaw(WebSocket conn, String buffer) {
        conn.send(buffer);
    }

    /**
     * 发送数据
     * @param conn
     * @param buffer
     */
    public void sendRaw(WebSocket conn, byte[] buffer) {
        conn.send(buffer);
    }

    /**
     * 序列化消息
     * @param message
     * @return
     */
    public String serializeMessageToString(BaseMessage message) {
        String ret = null;
        try {
            ret = this.serializeTool.serializeObjectAndStringify(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 序列化消息
     * @param message
     * @return
     */
    public byte[] serializeMessageToBytes(BaseMessage message) {
        byte[] ret = null;
        try {
            ret = this.serializeTool.serializeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 设置一个session的密钥/公钥和加密方式
     * @param conn
     * @param k
     * @param type
     */
    public void setConnectionKeyAndEncryptType(WebSocket conn, Key k, EncryptTypeEnum type) {
        Session currentSession = new Session(conn);
        this.sessionManager.setSessionEncryptType(currentSession, type);
        this.sessionManager.setSessionKey(currentSession, k);
    }

    /**
     * 获取默认公钥
     * @return
     */
    public PublicKey getDefaultPublicKey() {
        return this.defaultPublicKey;
    }

    /**
     * 获取默认私钥
     * @return
     */
    public PrivateKey getDefaultPrivateKey() {
        return this.defaultPrivateKey;
    }

}
