package TeamSeven.handler.clientside.ui;

import TeamSeven.common.message.client.ClientChatMessage;
import TeamSeven.handler.BaseHandler;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/18.
 */
public class ClientChatHandler extends BaseHandler {

    private ClientChatMessage msg;

    public ClientChatHandler(ClientChatMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.msg = msg;
    }

    @Override
    public void onHandle() {
        String content = this.msg.getContent();

    }
}