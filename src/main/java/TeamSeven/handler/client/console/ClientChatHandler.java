package TeamSeven.handler.client.console;

import TeamSeven.common.message.client.ChatRoomClientChatMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.ChatRoomServerConsole;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/18.
 */
public class ClientChatHandler extends BaseHandler {

    private ChatRoomClientChatMessage msg;
    private ChatRoomServerConsole serverConsole;

    public ClientChatHandler(ChatRoomClientChatMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.msg = msg;
        this.serverConsole = (ChatRoomServerConsole) applier;
    }

    @Override
    public void onHandle() {
        String content = this.msg.getContent();

    }
}