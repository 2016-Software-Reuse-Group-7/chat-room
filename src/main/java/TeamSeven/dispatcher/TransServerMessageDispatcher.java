package TeamSeven.dispatcher;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.client.ClientChatMessage;
import TeamSeven.common.message.client.ClientGroupChatMessage;
import TeamSeven.common.message.server.GroupMemberLoginMessage;
import TeamSeven.common.message.server.ServerDelayChatMessage;
import TeamSeven.handler.clientside.console.ServerGroupLoginHandler;
import TeamSeven.handler.serverside.console.ClientChatHandler;
import TeamSeven.handler.serverside.console.ClientGroupChatHandler;
import TeamSeven.handler.serverside.console.ServerDelayChatHandler;
import org.java_websocket.WebSocket;

/**
 * Created by zhao on 2016/5/31.
 */
public class TransServerMessageDispatcher extends MessageDispatcher {
    public TransServerMessageDispatcher(Object applier){
        super(applier);
    }

    @Override
    public void dispatch(BaseMessage message, WebSocket connFrom) {
        switch (message.getType()){
            case CLIENT_CHAT:
                handler = new ClientChatHandler((ClientChatMessage) message,connFrom,applier);
                break;
            case CLIENT_GROUP_CHAT:
                handler = new ClientGroupChatHandler((ClientGroupChatMessage) message,connFrom,applier);
                break;
            case SERVER_GROUP_CHAT:
                handler = new ServerGroupLoginHandler((GroupMemberLoginMessage) message,connFrom,applier);
                break;
            case SERVER_DELAY_CHAT:
                handler = new ServerDelayChatHandler((ServerDelayChatMessage) message, connFrom, applier);
                break;
            default:
                break;
        }
    }
}
