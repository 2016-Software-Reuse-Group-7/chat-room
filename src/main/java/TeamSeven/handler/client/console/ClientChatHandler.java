package TeamSeven.handler.client.console;

import TeamSeven.common.message.client.ClientChatMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.ChatRoomServerConsole;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/18.
 */
public class ClientChatHandler extends BaseHandler {

    private ClientChatMessage msg;
    private ChatRoomServerConsole serverConsole;

    public ClientChatHandler(ClientChatMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.msg = msg;
        this.serverConsole = (ChatRoomServerConsole) applier;
    }

    @Override
    public void onHandle() {
        serverConsole.consoleAppendLine( this.msg.getAccount().getUserId(), this.msg.getContent() );
    }
}