package TeamSeven.handler;

import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/18.
 */
public abstract class BaseHandler {

    protected WebSocket ws;

    public BaseHandler(WebSocket conn) {
        this.ws = conn;
    }

    public abstract void onHandle();
}
