package TeamSeven.dispatcher;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.client.ClientActionStartConnectionMessage;
import TeamSeven.common.message.server.ServerAskEncryptTypeMessage;
import TeamSeven.common.message.server.ServerAskLoginMessage;
import TeamSeven.common.message.server.ServerBoardcastMessage;
import TeamSeven.handler.clientside.console.ClientActionStartConnectionHandler;
import TeamSeven.handler.clientside.console.ServerAskEncryptTypeHandler;
import TeamSeven.handler.clientside.console.ServerAskLoginHandler;
import TeamSeven.handler.clientside.console.ServerBoardcastHandler;
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
                handler = new ServerBoardcastHandler( (ServerBoardcastMessage) message, connFrom, applier );
                break;
            case SERVER_SECKEY:
                break;
            case CLIENT_ACT_START_CONNECTION:
                handler = new ClientActionStartConnectionHandler( (ClientActionStartConnectionMessage) message, connFrom, applier );
                break;
            case SERVER_ASK_ENCRYPT_TYPE:
                handler = new ServerAskEncryptTypeHandler( (ServerAskEncryptTypeMessage) message, connFrom, applier );
                break;
            case SERVER_ASK_LOGIN:
                handler = new ServerAskLoginHandler( (ServerAskLoginMessage) message, connFrom, applier );
            case SERVER_RESP_LOGIN_SUCCESS:
                handler = null;  // TODO
            case SERVER_RESP_LOGIN_FAILED:
                handler = null;  // TODO
            default:
                break;
        }
        if (null != handler) {
            handler.onHandle();
        }
    }
}
