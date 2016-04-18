package TeamSeven.server.socket;

import TeamSeven.common.entity.Session;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.message.BaseMessage;
import TeamSeven.dispatcher.ConsoleServerSideMessageDispatcher;
import TeamSeven.server.session.SessionManager;
import TeamSeven.util.encrypt.AsymmertricCoder;
import TeamSeven.util.encrypt.SymmetricCoder;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;


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
        this.sessionManager.setSessionEncryptType(new Session(conn), null);
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
        // 消息可能已被加密, 需要检查并解密
        String encryptedMessageStr = null;
        try {
            encryptedMessageStr = (String) cs.deserializeStringifyObject(messageStr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Session currentSession = new Session(conn);
        EncryptTypeEnum encryptType = null;
        String decryptedMessageStr = null;

        if ( sessionManager.hasSession(currentSession) ) {
            encryptType = sessionManager.getSessionEncryptType(currentSession);
            if (encryptType == null) {
                decryptedMessageStr = encryptedMessageStr;
            }
            // 如果有加密
            else {
                // 对称加密
                if (encryptType.isSymmetricEncryption()) {
                    try {
                        SymmetricCoder sc = (SymmetricCoder) encryptType.getCoderClass().newInstance();
                        decryptedMessageStr = new String(
                            sc.decrypt(encryptedMessageStr.getBytes(), sessionManager.getSessionEncryptKey(currentSession))
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // 非对称加密
                else {
                    try {
                        AsymmertricCoder ac = (AsymmertricCoder) encryptType.getCoderClass().newInstance();
                        ac.setPublicKey((PublicKey) sessionManager.getSessionEncryptKey(currentSession));
                        decryptedMessageStr = new String(
                            ac.decryptWithPublicKey(encryptedMessageStr.getBytes())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            // 消息来自不明session?
            return;
        }

        BaseMessage msg;
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
    public void onError(WebSocket webSocket, Exception e) {
        // TODO: error handle
    }


}
