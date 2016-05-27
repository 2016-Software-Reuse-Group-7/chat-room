package TeamSeven.server.socket;

import TeamSeven.common.entity.Session;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.server.ServerAskEncryptTypeMessage;
import TeamSeven.dispatcher.ConsoleServerSideMessageDispatcher;
import TeamSeven.dispatcher.MessageDispatcher;
import TeamSeven.server.session.SessionManager;
import TeamSeven.util.encrypt.AsymmertricCoder;
import TeamSeven.util.encrypt.SymmetricCoder;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.PrivateKey;


/**
 * Created by joshoy on 16/4/18.
 */
public class ChatRoomServerSocketImpl extends ChatRoomServerSocket {

    public ChatRoomServerSocketImpl() throws UnknownHostException {
    }

    public ChatRoomServerSocketImpl(int port,
                                    MessageDispatcher dispatcher,
                                    SessionManager sessionManager) throws UnknownHostException {
        super(port, dispatcher, sessionManager);
        System.out.println("Starting server on port " + port);
        this.start();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
        this.sessionManager.addSession(new Session(conn));
        this.sessionManager.setSessionEncryptType(new Session(conn), null);
        this.dispatcher.dispatch(new ServerAskEncryptTypeMessage(), conn);
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

        String decryptedMessageStr = messageStr;

        BaseMessage msg = null;
        // 当一条被序列化的消息到达后

        try {
            msg = (BaseMessage) cs.deserializeStringifyObject(decryptedMessageStr);
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
    public void onError(WebSocket conn, Exception e) {
        this.sessionManager.removeSession(new Session(conn));
        conn.close();
        // TODO: error handle
    }
}
