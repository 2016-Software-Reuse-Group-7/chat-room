package TeamSeven.dispatcher;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.client.ClientActionStartConnectionMessage;
import TeamSeven.common.message.server.*;
import TeamSeven.handler.clientside.console.*;
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
        System.out.println("客户端接收到消息类型: " + message.getType().toString());
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
                break;
            case SERVER_RESP_LOGIN_SUCCESS:
                handler = new ServerRespLoginSuccessHandler( (ServerRespLoginSuccessMessage) message, connFrom, applier );
                break;
            case SERVER_RESP_LOGIN_FAILED:
                handler = new ServerRespLoginFailedHandler( (ServerRespLoginFailedMessage) message, connFrom, applier );
                break;
            default:
                break;
        }
        if (null == handler) {
            System.err.println("错误: 消息的handler还未定义! 消息类型为: " + message.getType().toString());
        }
        else {
            handler.onHandle();
        }
    }
}
