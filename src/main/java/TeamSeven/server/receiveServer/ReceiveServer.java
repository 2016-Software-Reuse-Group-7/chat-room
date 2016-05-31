package TeamSeven.server.receiveServer;

import TeamSeven.common.entity.Account;
import TeamSeven.common.entity.Session;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.message.BaseMessage;
import TeamSeven.dispatcher.LicenseServerMessageDispatcher;
import TeamSeven.dispatcher.MessageDispatcher;
import TeamSeven.server.session.SessionManager;
import TeamSeven.server.session.SessionManagerImpl;
import TeamSeven.server.socket.ChatRoomServerSocket;
import TeamSeven.server.socket.ChatRoomServerSocketImpl;
import TeamSeven.util.config.ConfigManager;
import TeamSeven.util.config.ConfigManagerImpl;
import TeamSeven.util.performace.PerformanceManager;
import TeamSeven.util.serialize.ChatRoomSerializer;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.java_websocket.WebSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import static java.lang.System.exit;

/**
 * Created by john on 2016/5/31.
 */
public class ReceiveServer {
    protected ConfigManager cm;
    protected MessageDispatcher dispatcher;
    protected SessionManager sessionManager;
    /**
     * 序列化工具
     */
    protected ChatRoomSerializer serializeTool;
    /*
    * 默认公钥/私钥
    * */
    protected PerformanceManager performanceManager;
    protected PublicKey defaultPublicKey;
    protected PrivateKey defaultPrivateKey;
    protected ChatRoomServerSocket ss;
    public ReceiveServer() {
        /* Init members */
        cm = new ConfigManagerImpl("demoServerConfig.conf");
        dispatcher = new LicenseServerMessageDispatcher(this);
        sessionManager = new SessionManagerImpl();

        int bindingPort = cm.getInt("receiveServer.port");

        try {
            ChatRoomServerSocket serverSocket = new ChatRoomServerSocketImpl(bindingPort, dispatcher, sessionManager);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
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
    public PerformanceManager getPerformanceManager() {
        return this.performanceManager;
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
    public static String p="src/main/resources/log4j-server.properties";

    private ReceiveServer serverConsole;

    public InputThread(ReceiveServer applier) {
        this.serverConsole = applier;
    }
    private static Logger logger = Logger.getLogger(ReceiveServer.class);

    public void run() {
        PropertyConfigurator.configure(p);//加载.properties文件
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
                logger.info("server退出");

                break;
            }
            else {
                // TODO: Boardcast
            }
        }
        return;
    }
}