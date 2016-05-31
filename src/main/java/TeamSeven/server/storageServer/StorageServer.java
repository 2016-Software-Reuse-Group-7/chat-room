package TeamSeven.server.storageServer;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.dispatcher.MessageDispatcher;
import TeamSeven.dispatcher.StorageServerMessageDispatcher;
import TeamSeven.server.account.AccountManager;
import TeamSeven.server.account.AccountManagerImpl;
import TeamSeven.server.session.SessionManager;
import TeamSeven.server.session.SessionManagerImpl;
import TeamSeven.server.socket.ChatRoomServerSocket;
import TeamSeven.server.socket.ChatRoomServerSocketImpl;
import TeamSeven.util.config.ConfigManager;
import TeamSeven.util.config.ConfigManagerImpl;
import TeamSeven.util.performace.PerformanceManager;
import TeamSeven.util.performace.PerformanceManagerImpl;
import TeamSeven.util.zip.ZipManager;
import TeamSeven.util.zip.ZipManagerImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.java_websocket.WebSocket;

import java.io.IOException;
import java.net.UnknownHostException;

import static java.lang.System.exit;

/**
 * Created by tina on 16/5/31.
 */
public class StorageServer {
    /*
    * tool managers
     */
    protected ConfigManager cm;
    protected PerformanceManager performanceManager;
    protected ZipManager zipManager;

    /*
    * 账号管理
    * */
    protected AccountManager accountManager = null;

    /*
    * logger 分级日志
     */
    private static Logger logger = Logger.getLogger(StorageServer.class);
    public static String p="src/main/resources/log4j-server.properties";



    protected MessageDispatcher dispatcher;
    protected SessionManager sessionManager;
    protected ChatRoomServerSocket serverSocket;


    public StorageServer() {

        cm = new ConfigManagerImpl("demoServerConfig.conf");
        try {
            this.performanceManager = new PerformanceManagerImpl();
            this.zipManager = new ZipManagerImpl();
        } catch (IOException e) {
            logger.error("初始化 Performance Manager 和 Zip Manager错误");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("初始化 Performance Manager 和 Zip Manager错误");
            e.printStackTrace();
        }
        PropertyConfigurator.configure(p);//加载.properties文件
        this.accountManager = new AccountManagerImpl();

        dispatcher = new StorageServerMessageDispatcher(this);
        sessionManager = new SessionManagerImpl();

        int bindingPort = cm.getInt("storageServer.port");

        try {
            ChatRoomServerSocket serverSocket = new ChatRoomServerSocketImpl(bindingPort, dispatcher, sessionManager);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // 创建Server WebSocket
        try {
            serverSocket = new ChatRoomServerSocketImpl(bindingPort, dispatcher, sessionManager);

        } catch (UnknownHostException e) {
            logger.error("server创建错误");
            e.printStackTrace();
        }
    }

    /* *
     *  +------------------+
     *  | API for handlers |
     *  +------------------+
     * */

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
     * 获取 server performance manager
     * @return
     */
    public PerformanceManager getPerformanceManager() {
        return this.performanceManager;
    }

    /**
     * 获取 server zip manager
     * @return
     */
    public ZipManager getZipManager() {
        return this.zipManager;
    }

    /**
     * message record
     * @return
     */
    public String messageRecord(String clientId, String message) throws Exception {
        String content = "[" + clientId + "] " + message;
        this.getZipManager().messageRecord(content);
        return content;
    }
    public String groupMessageRecord(String clientId, String message) throws Exception {
        String content = "[" + clientId + "](group chat) " + message;
        this.getZipManager().messageRecord(content);
        return content;
    }

    /**
     * @return
     */
    public AccountManager getAccountManager() {
        return this.accountManager;
    }


    /**
     * 把消息发送到转发server
     * @param message
     */
    public void sendMessageToTransServer(BaseMessage message) {

    }


    /**
     * 关闭Server
     */
    public void closeServer() {
        try {
            this.serverSocket.stop();
            this.getPerformanceManager().endLog();
            this.getZipManager().endZip();
            exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
