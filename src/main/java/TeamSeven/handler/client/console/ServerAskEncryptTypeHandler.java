package TeamSeven.handler.client.console;

import TeamSeven.client.ChatRoomClientConsole;
import TeamSeven.common.message.server.ServerAskEncrypyTypeMessage;
import TeamSeven.handler.BaseHandler;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/22.
 */
public class ServerAskEncryptTypeHandler extends BaseHandler {

    ServerAskEncrypyTypeMessage message;
    ChatRoomClientConsole clientConsole;

    public ServerAskEncryptTypeHandler(ServerAskEncrypyTypeMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.message = msg;
        this.clientConsole = (ChatRoomClientConsole) applier;
    }

    @Override
    public void onHandle() {
        // TODO
    }
}
