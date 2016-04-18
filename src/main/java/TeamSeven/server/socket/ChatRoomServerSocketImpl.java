package TeamSeven.server.socket;

import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.exception.ChatRoomUndefinedMessageTypeException;
import TeamSeven.common.message.ChatRoomBaseMessage;
import TeamSeven.util.serialize.ChatRoomSerializer;
import TeamSeven.util.serialize.ChatRoomSerializerImpl;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;


/**
 * Created by joshoy on 16/4/18.
 */
public class ChatRoomServerSocketImpl  extends WebSocketServer implements ChatRoomServerSocket {
    /**
     * 绑定的端口号
     */
    protected int port;
    ChatRoomSerializer cs;

    public ChatRoomServerSocketImpl(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        this.port = port;
        cs = new ChatRoomSerializerImpl();
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
            this.onHandle(msg, conn);
        }
        catch (IOException err) {
            System.err.println("[!!!Exception!!!]: Received message IOException.");
        }
        catch (ClassNotFoundException err) {
            System.err.println("[!!!Exception!!!]: Received message cannot transfer into ChatRoomBaseMessage.");
        }
        catch (ChatRoomUndefinedMessageTypeException e) {
            e.printErrorMessage();
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
    public void onHandle(ChatRoomBaseMessage msg, WebSocket conn) throws ChatRoomUndefinedMessageTypeException {

    }


}
