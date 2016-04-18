package TeamSeven.handler.client.ui;

import TeamSeven.common.message.client.ChatRoomClientChatMessage;
import TeamSeven.handler.BaseHandler;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/18.
 */
public class ClientChatHandler extends BaseHandler {

    private ChatRoomClientChatMessage msg;

    public ClientChatHandler(ChatRoomClientChatMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.msg = msg;
    }

    @Override
    public void onHandle() {
        String content = this.msg.getContent();

    }
}