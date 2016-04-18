package TeamSeven.handler.server;

import TeamSeven.handler.ChatRoomBaseHandler;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/18.
 */
public abstract class ChatRoomServerHandler extends ChatRoomBaseHandler {

    WebSocket clientConn = null;

    public ChatRoomServerHandler(WebSocket clientConn) {
        this.clientConn = clientConn;
    }

    public WebSocket getClientConn() {
        return this.clientConn;
    }
}
