package TeamSeven.dispatcher;

import TeamSeven.common.message.ChatRoomBaseMessage;
import TeamSeven.handler.BaseHandler;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/18.
 */
public abstract class MessageDispatcher {

    public BaseHandler handler;
    public Object applier;

    public MessageDispatcher(Object applier) {
        this.applier = applier;
    }

    public abstract void dispatch(ChatRoomBaseMessage message, WebSocket connFrom);
}
