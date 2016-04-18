package TeamSeven.server.socket;

import TeamSeven.dispatcher.ConsoleServerSideMessageDispatcher;
import TeamSeven.dispatcher.MessageDispatcher;
import TeamSeven.util.serialize.ChatRoomSerializer;
import TeamSeven.util.serialize.ChatRoomSerializerImpl;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by joshoy on 16/4/18.
 */
public abstract class ChatRoomServerSocket extends WebSocketServer {

    /**
     * 绑定的端口号
     */
    protected int port;
    ChatRoomSerializer cs;
    MessageDispatcher dispatcher;

    public ChatRoomServerSocket() throws UnknownHostException {
    }

    public ChatRoomServerSocket(int port, MessageDispatcher dispatcher) throws UnknownHostException {
        super(new InetSocketAddress(port));
        this.port = port;
        this.dispatcher = dispatcher;
        cs = new ChatRoomSerializerImpl();
    }

    public abstract void onOpen(WebSocket conn, ClientHandshake clientHandshake);
    public abstract void onClose(WebSocket conn, int code, String reason, boolean closeByRemote);
    public abstract void onMessage(WebSocket conn, String messageStr);
    public abstract void onError(WebSocket webSocket, Exception e);
}
