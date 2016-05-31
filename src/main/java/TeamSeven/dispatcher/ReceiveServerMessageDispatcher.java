package TeamSeven.dispatcher;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.handler.receiveServer.receiveServerHandler;
import TeamSeven.server.receiveServer.ReceiveServer;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/5/27.
 */
public class ReceiveServerMessageDispatcher extends MessageDispatcher {

    public ReceiveServerMessageDispatcher(Object applier) {
        super(applier);
    }

    @Override
    public void dispatch(BaseMessage message, WebSocket connFrom) {
        switch (message.getType()) {
            case SERVER_RECEIVE:
                handler = new receiveServerHandler(message, connFrom, applier);
                break;
            default:
                System.out.println("Message type not found.");
                break;
        }
    }
}
