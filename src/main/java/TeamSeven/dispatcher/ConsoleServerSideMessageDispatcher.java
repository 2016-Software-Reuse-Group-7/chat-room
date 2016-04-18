package TeamSeven.dispatcher;

import TeamSeven.common.message.ChatRoomBaseMessage;
import TeamSeven.common.message.client.ChatRoomClientChatMessage;
import TeamSeven.handler.client.console.ClientChatHandler;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/18.
 */
public class ConsoleServerSideMessageDispatcher extends MessageDispatcher {

    public ConsoleServerSideMessageDispatcher(Object applier) {
        super(applier);
    }

    @Override
    public void dispatch(ChatRoomBaseMessage message, WebSocket conn)  {
        switch (message.getType()) {
            case CLIENT_CHAT:
                ChatRoomClientChatMessage transMsg = (ChatRoomClientChatMessage) message;
                handler = new ClientChatHandler(transMsg, conn, applier);
                break;
            default:
                break;
        }
        handler.onHandle();
    }
}
