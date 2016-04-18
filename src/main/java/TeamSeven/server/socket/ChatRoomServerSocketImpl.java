package TeamSeven.server.socket;

import TeamSeven.common.entity.Session;
import TeamSeven.common.message.BaseMessage;
import TeamSeven.dispatcher.ConsoleServerSideMessageDispatcher;
import TeamSeven.server.session.SessionManager;
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

    public ChatRoomServerSocketImpl(int port,
                                    ConsoleServerSideMessageDispatcher dispatcher,
                                    SessionManager sessionManager) throws UnknownHostException {
        super(port, dispatcher, sessionManager);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
        this.sessionManager.addSession(new Session(conn));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean closeByRemote) {
        this.sessionManager.removeSession(new Session(conn));
    }

    /**
     * @param messageStr:  收到的消息
     * @param conn: 发送该消息的客户端连接
     */
    @Override
    public void onMessage(WebSocket conn, String messageStr) {
        BaseMessage msg;
        // 当一条被序列化的消息到达后
        try {
            msg = (BaseMessage) cs.deserializeStringifyObject(messageStr);
            // 交给dispatcher处理
            dispatcher.dispatch(msg, conn);
        }
        catch (IOException err) {
            System.err.println("[!!!Exception!!!]: Received message IOException.");
        }
        catch (ClassNotFoundException err) {
            System.err.println("[!!!Exception!!!]: Received message cannot transfer into BaseMessage.");
        } finally {
            // Do nothing, just go on.
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        // TODO: error handle
    }


}
