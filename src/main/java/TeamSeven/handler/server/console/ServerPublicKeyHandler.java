package TeamSeven.handler.server.console;

import TeamSeven.client.ChatRoomClientConsole;
import TeamSeven.common.message.server.ServerPublicKeyMessage;
import TeamSeven.handler.BaseHandler;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/19.
 */
public class ServerPublicKeyHandler extends BaseHandler {

    ServerPublicKeyMessage message;
    ChatRoomClientConsole clientConsole;

    public ServerPublicKeyHandler(ServerPublicKeyMessage msg, WebSocket conn, Object consoleObject) {
        super(conn);
        this.message = msg;
        this.clientConsole = (ChatRoomClientConsole) consoleObject;
    }

    @Override
    public void onHandle() {
        this.clientConsole.setServerPublicKey(this.message.getServerPublicKey());
    }
}
