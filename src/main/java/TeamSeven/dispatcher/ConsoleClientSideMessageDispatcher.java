package TeamSeven.dispatcher;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.server.ServerBoardcastMessage;
import TeamSeven.common.message.server.ServerPublicKeyMessage;
import TeamSeven.handler.server.console.ServerBoardcastHandler;
import TeamSeven.handler.server.console.ServerPublicKeyHandler;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/19.
 */
public class ConsoleClientSideMessageDispatcher extends MessageDispatcher {

    public ConsoleClientSideMessageDispatcher(Object applier) {
        super(applier);
    }


    @Override
    public void dispatch(BaseMessage message, WebSocket connFrom) {
        handler = null;
        switch (message.getType()) {
            case SERVER_ACK:
                break;
            case SERVER_BOARDCAST:
                handler = new ServerBoardcastHandler( (ServerBoardcastMessage) message , connFrom, applier );
                break;
            case SERVER_PUBKEY:
                handler = new ServerPublicKeyHandler( (ServerPublicKeyMessage) message, connFrom, applier );
                break;
            case SERVER_SECKEY:
                break;
            default:
                break;
        }
        if (null != handler) {
            handler.onHandle();
        }
    }
}
