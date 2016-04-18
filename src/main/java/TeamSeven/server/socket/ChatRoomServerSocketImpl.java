package TeamSeven.server.socket;

import TeamSeven.common.message.ChatRoomBaseMessage;
import TeamSeven.dispatcher.ConsoleServerSideMessageDispatcher;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.io.IOException;
import java.net.UnknownHostException;


/**
 * Created by joshoy on 16/4/18.
 */
public class ChatRoomServerSocketImpl extends ChatRoomServerSocket {

    public ChatRoomServerSocketImpl() throws UnknownHostException {
    }

    public ChatRoomServerSocketImpl(int port, ConsoleServerSideMessageDispatcher dispatcher) throws UnknownHostException {
        super(port, dispatcher);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
        // TODO: Handle client starting a connection
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean closeByRemote) {
        // TODO: Handle close
    }

    @Override
    public void onMessage(WebSocket conn, String messageStr) {
        ChatRoomBaseMessage msg;
        // 当一条被序列化的消息到达后
        try {
            msg = (ChatRoomBaseMessage) cs.deserializeStringifyObject(messageStr);
            // 交给dispatcher处理
            dispatcher.dispatch(msg, conn, this);
        }
        catch (IOException err) {
            System.err.println("[!!!Exception!!!]: Received message IOException.");
        }
        catch (ClassNotFoundException err) {
            System.err.println("[!!!Exception!!!]: Received message cannot transfer into ChatRoomBaseMessage.");
        } finally {
            // Do nothing, just go on.
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    /**
     * @param msg:  收到的消息
     * @param conn: 发送该消息的客户端连接
     */


}
