package TeamSeven.handler.server.console;

import TeamSeven.client.ChatRoomClientConsole;
import TeamSeven.common.message.server.ServerBoardcastMessage;
import TeamSeven.handler.BaseHandler;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/19.
 */
public class ServerBoardcastHandler extends BaseHandler {

    private ServerBoardcastMessage message;
    private ChatRoomClientConsole clientConsole;

    public ServerBoardcastHandler(ServerBoardcastMessage msg, WebSocket conn, Object clientConsole) {
        super(conn);
        this.message = msg;
        this.clientConsole = (ChatRoomClientConsole) clientConsole;
    }

    @Override
    public void onHandle() {

    }
}
