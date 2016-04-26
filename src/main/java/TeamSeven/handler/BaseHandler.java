package TeamSeven.handler;

import org.java_websocket.WebSocket;
import org.apache.logging.log4j.*;

/**
 * Created by joshoy on 16/4/18.
 */
public abstract class BaseHandler {

    private static final Logger logger = LogManager.getLogger(BaseHandler.class);
    protected WebSocket ws;

    public BaseHandler(WebSocket conn) {
        this.ws = conn;
    }

    public abstract void onHandle();
}
