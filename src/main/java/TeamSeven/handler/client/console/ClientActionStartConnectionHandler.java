package TeamSeven.handler.client.console;

import TeamSeven.client.ChatRoomClientConsole;
import TeamSeven.common.message.client.ClientActionStartConnectionMessage;
import TeamSeven.handler.BaseHandler;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/21.
 */
public class ClientActionStartConnectionHandler extends BaseHandler {

    protected ClientActionStartConnectionMessage message;
    protected ChatRoomClientConsole clientConsole;

    public ClientActionStartConnectionHandler(ClientActionStartConnectionMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.message = msg;
        this.clientConsole = (ChatRoomClientConsole) applier;
    }

    @Override
    public void onHandle() {
        this.clientConsole.startConnection(this.message.getServerUri());
    }
}
