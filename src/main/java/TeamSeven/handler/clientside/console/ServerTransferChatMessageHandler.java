package TeamSeven.handler.clientside.console;

import TeamSeven.client.ChatRoomClientConsole;
import TeamSeven.common.message.server.ServerTransferChatMessage;
import TeamSeven.handler.BaseHandler;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/26.
 */
public class ServerTransferChatMessageHandler extends BaseHandler {

    protected ServerTransferChatMessage message;
    protected ChatRoomClientConsole clientConsole;

    public ServerTransferChatMessageHandler(ServerTransferChatMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.message = msg;
        this.clientConsole = (ChatRoomClientConsole) applier;
    }

    @Override
    public void onHandle() {
        String prefix = message.getSenderId() + "(" + message.getChatTime().toString() + ")";
        String content = message.getContent();
        this.clientConsole.consoleAppendLine(prefix, content);
    }
}
