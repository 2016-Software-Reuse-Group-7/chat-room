package TeamSeven.dispatcher;

import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.client.ClientChatMessage;
import TeamSeven.common.message.client.ClientGroupChatMessage;
import TeamSeven.common.message.server.GroupMemberLoginMessage;
import TeamSeven.handler.clientside.console.ServerGroupLoginHandler;
import TeamSeven.handler.serverside.console.ClientGroupChatHandler;
import TeamSeven.handler.storageServer.ClientChatStorageHandler;
import org.java_websocket.WebSocket;

/**
 * Created by tina on 16/5/31.
 */
public class StorageServerMessageDispatcher extends MessageDispatcher {

    public StorageServerMessageDispatcher(Object applier) {
        super(applier);
    }

    @Override
    public void dispatch(BaseMessage message, WebSocket connFrom) {
        switch (message.getType()){
            case CLIENT_CHAT:
                handler = new ClientChatStorageHandler((ClientChatMessage) message, connFrom, applier);
                break;
            case CLIENT_GROUP_CHAT:
                handler = new ClientGroupChatHandler((ClientGroupChatMessage) message,connFrom,applier);
                break;
            case SERVER_GROUP_CHAT:
                handler = new ServerGroupLoginHandler((GroupMemberLoginMessage) message,connFrom,applier);
                break;
            default:
                break;
        }
    }
}
