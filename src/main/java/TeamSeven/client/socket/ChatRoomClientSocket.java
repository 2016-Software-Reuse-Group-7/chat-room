package TeamSeven.client.socket;

import TeamSeven.dispatcher.MessageDispatcher;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;

import java.net.URI;
import java.util.Map;

/**
 * Created by joshoy on 16/4/17.
 */
public abstract class ChatRoomClientSocket extends WebSocketClient {

    protected URI serverUri;
    protected MessageDispatcher dispatcher;
    protected Object handleApplier;

    /**
     * @param serverUri  WebSocket Server URI
     * @param dispatcher
     */
    public ChatRoomClientSocket(URI serverUri, MessageDispatcher dispatcher, Object appiler) {
        super(serverUri);
        this.serverUri = serverUri;
        this.dispatcher = dispatcher;
        this.handleApplier = appiler;
    }

}
