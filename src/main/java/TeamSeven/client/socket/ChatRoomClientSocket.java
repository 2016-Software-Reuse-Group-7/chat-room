package TeamSeven.client.socket;

import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.dispatcher.MessageDispatcher;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;

import java.net.URI;
import java.security.Key;
import java.util.Map;

/**
 * Created by joshoy on 16/4/17.
 */
public abstract class ChatRoomClientSocket extends WebSocketClient {

    protected URI serverUri;
    protected MessageDispatcher dispatcher;

    /**
     * @param serverUri  WebSocket Server URI
     * @param dispatcher
     */
    public ChatRoomClientSocket(URI serverUri, MessageDispatcher dispatcher) {
        super(serverUri);
        this.serverUri = serverUri;
        this.dispatcher = dispatcher;
    }

    public abstract void setEncryptType(EncryptTypeEnum type);

    public abstract void setConnectionKey(Key k);
}
