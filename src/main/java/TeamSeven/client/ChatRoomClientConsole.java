package TeamSeven.client;

import TeamSeven.client.socket.ChatRoomClientSocket;
import TeamSeven.client.socket.ChatRoomClientSocketImpl;
import TeamSeven.common.entity.Account;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.client.ClientChatMessage;
import TeamSeven.common.message.client.ClientLoginMessage;
import TeamSeven.common.message.server.ServerRespLoginSuccessMessage;
import TeamSeven.dispatcher.ConsoleClientSideMessageDispatcher;
import TeamSeven.dispatcher.MessageDispatcher;
import TeamSeven.util.config.ConfigManager;
import TeamSeven.util.config.ConfigManagerImpl;
import TeamSeven.util.encrypt.AsymmertricCoder;
import TeamSeven.util.encrypt.SymmetricCoder;
import TeamSeven.util.performace.PerformanceManager;
import TeamSeven.util.performace.PerformanceManagerImpl;
import TeamSeven.util.serialize.ChatRoomSerializer;
import TeamSeven.util.serialize.ChatRoomSerializerImpl;
import TeamSeven.util.zip.ZipManager;
import TeamSeven.util.zip.ZipManagerImpl;
import org.java_websocket.WebSocket;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Date;

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
    protected MessageDispatcher dispatcher;
    /* performance manager */
    protected PerformanceManager performanceManager;
    /* zip manager */
    protected ZipManager zipManager;
    /* 配置管理 */
    protected ConfigManager configManager;

    /* 是否处于已登录状态 */
    protected Boolean isLogged;
    /* 账号 */
    protected Account loggedAccount;

    public ChatRoomClientConsole(String configFileName) {

        this.dispatcher = new ConsoleClientSideMessageDispatcher(this);
        this.connectionEncryptType = null;
        this.serializeTool = new ChatRoomSerializerImpl();
        this.isLogged = false;
        this.configManager = new ConfigManagerImpl(configFileName);

        /* 初始化server uri */
        try {
            this.serverUri = new URI(
                "ws://" + this.configManager.getString("connection.host") + ":" + this.configManager.getInt("connection.port")
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        /* 初始化 performace manager */
        try {
            this.performanceManager = new PerformanceManagerImpl("client@" + (new Date()).toString());
            this.zipManager = new ZipManagerImpl("client@" + (new Date()).toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
     */
    public void startConnection() {
        System.out.println("Connecting to URI: " + this.serverUri.toString());
        this.clientSocket = new ChatRoomClientSocketImpl(serverUri, this.dispatcher, this.connectionEncryptType);
        this.clientSocket.connect();
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
        System.out.println("向服务器发送消息: " + msg.getType().toString());
        try {
            String serializedMessage = this.serializeTool.serializeObjectAndStringify(msg);
            String sendingBuffer = null;

            sendingBuffer = serializedMessage;
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
    public void selfDispatch(BaseMessage message) {
        this.dispatcher.dispatch(message, this.clientSocket.getConnection());
    }

    public void selfDispatch(BaseMessage message, WebSocket conn) {
        System.out.println("Dispatch message: " + message.toString());
        this.dispatcher.dispatch(message, conn);
    }

    /**
     * 设置连接密钥
     * @param k
     */
    public void setConnectionEncryptKey(Key k) {
        this.clientSocket.setConnectionKey(k);
    }

    /* 是否处于登录状态 */
    public Boolean getLogged() {
        return this.isLogged;
    }

    public void setLogged(Boolean b) {
        this.isLogged = b;
    }

    public ChatRoomClientSocket getClientSocket() {
        return this.clientSocket;
    }

    public void consoleAppendLine(String prefix, String line) {
        System.out.println("[" + prefix + "] " + line);
    }

    public Account UserEnterAccount() {
        System.out.println("服务器请求登录.");
        BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));

        try {

            System.out.print("请输入用户ID: ");
            String userId = sysin.readLine();
            System.out.print("请输入密码: ");
            String password = sysin.readLine();

            Account account = new Account(userId, password);
            this.setLoggedAccount(account);
            return account;
            // clientConsole.sendMessageWithEncryption(new ClientLoginMessage(account));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void startChat() {
        InputThread th = new InputThread(this);
        ( new Thread(th) ).start();
    }

    public PerformanceManager getPerformanceManager() {
        return this.performanceManager;
    }

    public Account getLoggedAccount() {
        return loggedAccount;
    }

    public void setLoggedAccount(Account loggedAccount) {
        this.loggedAccount = loggedAccount;
    }

}

class InputThread implements Runnable {

    private ChatRoomClientConsole clientConsole;

    public InputThread(ChatRoomClientConsole applier) {
        this.clientConsole = applier;
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
                clientConsole.clientSocket.close();
                /* Performance manager */
                try {
                    clientConsole.getPerformanceManager().endLog();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            else {
                ClientChatMessage chatMessage = new ClientChatMessage(chatContent);
                clientConsole.sendMessageWithEncryption(chatMessage);
                /* Performance manager */
                try {
                    clientConsole.getPerformanceManager().clientAddMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /* Dispatch new message to self */
                clientConsole.selfDispatch(new ServerRespLoginSuccessMessage());
                break;
            }
        }
        return;
    }
}