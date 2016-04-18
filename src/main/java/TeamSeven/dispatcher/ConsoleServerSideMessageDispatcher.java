package TeamSeven.dispatcher;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.client.ClientChatMessage;
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
    public void dispatch(BaseMessage message, WebSocket conn)  {
        switch (message.getType()) {
            case CLIENT_CHAT:
                ClientChatMessage transMsg = (ClientChatMessage) message;
                handler = new ClientChatHandler(transMsg, conn, applier);
                break;
            default:
                break;
        }
        handler.onHandle();
    }
}
