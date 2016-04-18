package TeamSeven.dispatcher;

import TeamSeven.common.message.BaseMessage;
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

    public abstract void dispatch(BaseMessage message, WebSocket connFrom);
}
