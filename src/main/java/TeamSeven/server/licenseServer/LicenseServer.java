package TeamSeven.server.licenseServer;

import TeamSeven.dispatcher.LicenseServerMessageDispatcher;
import TeamSeven.dispatcher.MessageDispatcher;
import TeamSeven.server.session.SessionManager;
import TeamSeven.server.session.SessionManagerImpl;
import TeamSeven.server.socket.ChatRoomServerSocket;
import TeamSeven.server.socket.ChatRoomServerSocketImpl;
import TeamSeven.util.config.ConfigManager;
import TeamSeven.util.config.ConfigManagerImpl;
import TeamSeven.util.serialize.ChatRoomSerializer;

import java.net.UnknownHostException;

/**
 * Created by joshoy on 16/5/27.
 */
public class LicenseServer {

    protected ConfigManager cm;
    protected MessageDispatcher dispatcher;
    protected SessionManager sessionManager;

    public LicenseServer() {
        /* Init members */
        cm = new ConfigManagerImpl("demoServerConfig.conf");
        dispatcher = new LicenseServerMessageDispatcher(this);
        sessionManager = new SessionManagerImpl();

        int bindingPort = cm.getInt("licenseServer.port");

        try {
            ChatRoomServerSocket serverSocket = new ChatRoomServerSocketImpl(bindingPort, dispatcher, sessionManager);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


}
