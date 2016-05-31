package TeamSeven.server.transServer;

import TeamSeven.dispatcher.MessageDispatcher;
import TeamSeven.dispatcher.TransServerMessageDispatcher;
import TeamSeven.server.session.SessionManager;
import TeamSeven.server.session.SessionManagerImpl;
import TeamSeven.server.socket.ChatRoomServerSocket;
import TeamSeven.server.socket.ChatRoomServerSocketImpl;
import TeamSeven.util.config.ConfigManager;
import TeamSeven.util.config.ConfigManagerImpl;

import java.net.UnknownHostException;

/**
 * Created by zhao on 2016/5/31.
 */
public class transServer {
    // 声明 创建host所需的基本参数
    protected ConfigManager configManager;
    protected MessageDispatcher dispatcher;
    protected SessionManager sessionManager;

    public transServer() {
        configManager = new ConfigManagerImpl("demoServerConfig.conf");
        dispatcher = new TransServerMessageDispatcher(this);
        sessionManager = new SessionManagerImpl();

        //  读取端口号的配置信息
        int bindingPort = configManager.getInt("transServer.port");
        try{
            ChatRoomServerSocket serverSocket = new ChatRoomServerSocketImpl(bindingPort,dispatcher,sessionManager);
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
    }

    public void printLine(String s) {
        System.out.println(s);
    }

}
