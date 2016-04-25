package TeamSeven.dispatcher;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.client.ClientChatMessage;
import TeamSeven.common.message.client.ClientRespEncryptTypeMessage;
import TeamSeven.handler.serverside.console.ClientChatHandler;
import TeamSeven.handler.serverside.console.ClientRespEncryptTypeHandler;
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
                handler = new ClientChatHandler((ClientChatMessage) message, conn, applier);
                break;
            case CLIENT_RESP_ENCRYPT_TYPE:
                handler = new ClientRespEncryptTypeHandler((ClientRespEncryptTypeMessage) message, conn, applier);
                break;
            default:
                break;
        }
        handler.onHandle();
    }
}
