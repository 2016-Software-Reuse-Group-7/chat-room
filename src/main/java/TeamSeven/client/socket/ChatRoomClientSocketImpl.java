package TeamSeven.client.socket;

import TeamSeven.dispatcher.MessageDispatcher;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Created by joshoy on 16/4/17.
 */
public class ChatRoomClientSocketImpl extends ChatRoomClientSocket {
    /**
     * @param serverUri  WebSocket Server URI
     * @param dispatcher
     */
    public ChatRoomClientSocketImpl(URI serverUri, MessageDispatcher dispatcher, Object applier) {
        super(serverUri, dispatcher, applier);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        // TODO: on server connected
    }

    @Override
    public void onMessage(String serverMsg) {
        // TODO
    }

    @Override
    public void onClose(int code, String reason, boolean closeByRemote) {
        // TODO
    }

    @Override
    public void onError(Exception e) {
        // TODO
    }
}
