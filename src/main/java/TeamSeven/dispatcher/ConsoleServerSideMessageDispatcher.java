package TeamSeven.dispatcher;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.client.ClientChatMessage;
import TeamSeven.common.message.client.ClientGroupChatMessage;
import TeamSeven.common.message.client.ClientLoginMessage;
import TeamSeven.common.message.client.ClientRespEncryptTypeMessage;
import TeamSeven.common.message.server.ServerAskEncryptTypeMessage;
import TeamSeven.handler.serverside.console.*;
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

        System.out.println("服务端接收到消息类型: " + message.getType().toString());

        switch (message.getType()) {
            case CLIENT_CHAT:
                handler = new ClientChatHandler((ClientChatMessage) message, conn, applier);
                break;
            case CLIENT_RESP_ENCRYPT_TYPE:
                handler = new ClientRespEncryptTypeHandler((ClientRespEncryptTypeMessage) message, conn, applier);
                break;
            case SERVER_ASK_ENCRYPT_TYPE:
                handler = new ServerAskEncryptTypeHandler((ServerAskEncryptTypeMessage) message, conn, applier);
                break;
            case CLIENT_LOGIN:
                handler = new ClientLoginHandler( (ClientLoginMessage) message, conn, applier );
                break;
            /**
             *  服务器分发  群组聊天的 信息
             */
            case CLIENT_GROUP_CHAT:
                handler = new ClientGroupChatHandler((ClientGroupChatMessage) message,conn,applier);
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
