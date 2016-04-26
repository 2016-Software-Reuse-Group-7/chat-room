package TeamSeven.server;

import TeamSeven.common.entity.Account;
import TeamSeven.common.entity.Session;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.client.ClientChatMessage;
import TeamSeven.common.message.server.ServerRespLoginSuccessMessage;
import TeamSeven.dispatcher.ConsoleServerSideMessageDispatcher;
import TeamSeven.server.account.AccountManager;
import TeamSeven.server.account.AccountManagerImpl;
import TeamSeven.server.session.SessionManager;
import TeamSeven.server.session.SessionManagerImpl;
import TeamSeven.server.socket.ChatRoomServerSocket;
import TeamSeven.server.socket.ChatRoomServerSocketImpl;
import TeamSeven.util.config.ConfigManager;
import TeamSeven.util.config.ConfigManagerImpl;
import TeamSeven.util.encrypt.AES.AESCoder;
import TeamSeven.util.encrypt.AsymmertricCoder;
import TeamSeven.util.encrypt.RSA.RSACoder;
import TeamSeven.util.encrypt.SymmetricCoder;
import TeamSeven.util.performace.PerformanceManager;
import TeamSeven.util.performace.PerformanceManagerImpl;
import TeamSeven.util.serialize.ChatRoomSerializer;
import TeamSeven.util.serialize.ChatRoomSerializerImpl;
import org.java_websocket.WebSocket;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.security.*;
import java.util.Collection;

import static java.lang.System.exit;

/**
 * Created by joshoy on 16/4/17.
 */
public class ChatRoomServerConsole {

    protected int port;
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

    /*
    * Performance Manager
    * */
    protected PerformanceManager performanceManager;

    /**
     * 序列化工具
     */
    protected ChatRoomSerializer serializeTool;

    /*
    * 配置管理
    * */
    protected ConfigManager configManager;

    protected ConsoleServerSideMessageDispatcher dispatcher;
    protected ChatRoomServerSocket ss;

    public ChatRoomServerConsole(String configFileName) {
        // 创建一个Console Dispatcher
        // 将this作为applier参数传入后, 我们在自定义的handler里就可以直接调用本类提供的方法
        this.serializeTool = new ChatRoomSerializerImpl();
        this.dispatcher = new ConsoleServerSideMessageDispatcher(this);
        this.accountManager = new AccountManagerImpl();
        this.sessionManager = new SessionManagerImpl();
        this.configManager = new ConfigManagerImpl(configFileName);

        /* 从配置文件中读取bind的端口号 */
        this.port = this.configManager.getInt("server.port");
        if (port == 0) {
            System.out.println("请在配置文件中设置Server绑定的端口号.");
            exit(0);
        }

        /* 初始化 Performance Manager */
        this.performanceManager = new PerformanceManagerImpl();
        try {
            this.performanceManager.initServerPm();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            System.out.println("Server WebSocket Created.");
            // 开始输入
            this.startInput();
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
     * 把消息发送到客户端, 加密类型从session manager中读取
     * @param conn
     * @param message
     */
    public void sendMessageToClient(WebSocket conn, BaseMessage message) {
        System.out.println("向" + conn.getRemoteSocketAddress().toString() + "发送消息: " + message.getType().toString());
        Session currentSession = new Session(conn);
        // String encodedMsgBuffer = null;
        String sendingBuffer = null;
        try {
            // encodedMsgBuffer = this.serializeMessageToString(message);
            EncryptTypeEnum eType = this.sessionManager.getSessionEncryptType(currentSession);
            if ( true ) {
                sendingBuffer = this.serializeMessageToString(message);
            }
            this.sendRaw(conn, sendingBuffer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * 客户端登录
     * @param conn
     * @param account
     * @return
     */
    public boolean clientLogin(WebSocket conn, Account account) {
        boolean result = this.accountManager.accountLogin(conn, account);
        return result;
    }

    /**
     * @return
     */
    public AccountManager getAccountManager() {
        return this.accountManager;
    }

    /**
     * 向当前所有的connection发送消息
     * @param message
     */
    public void sendMessageToAll(BaseMessage message) {
        Collection<WebSocket> con = this.ss.connections();
        synchronized (con) {
            for (WebSocket c : con) {
                this.sendMessageToClient(c, message);
            }
        }
    }

    /**
     * 获取 server performance manager
     * @return
     */
    public PerformanceManager getPerformanceManager() {
        return this.performanceManager;
    }

    /**
     * 获取 config manager
     * @return
     */
    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    /**
     * 关闭Server
     */
    public void closeServer() {
        try {
            this.ss.stop();
            this.getPerformanceManager().endLog();
            exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startInput() {
        InputThread th = new InputThread(this);
        ( new Thread(th) ).start();
    }
}

class InputThread implements Runnable {

    private ChatRoomServerConsole serverConsole;

    public InputThread(ChatRoomServerConsole applier) {
        this.serverConsole = applier;
    }

    public void run() {
        while (true) {
            BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
            String chatContent = null;
            try {
                chatContent = sysin.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            if (chatContent.equals("exit")) {
                serverConsole.closeServer();
                break;
            }
            else {
                // TODO: Boardcast
            }
        }
        return;
    }
}
