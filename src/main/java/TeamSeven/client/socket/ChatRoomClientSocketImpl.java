package TeamSeven.client.socket;

import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.message.BaseMessage;
import TeamSeven.dispatcher.MessageDispatcher;
import TeamSeven.util.encrypt.AsymmertricCoder;
import TeamSeven.util.encrypt.SymmetricCoder;
import TeamSeven.util.serialize.ChatRoomSerializer;
import TeamSeven.util.serialize.ChatRoomSerializerImpl;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.security.Key;
import java.security.PublicKey;

/**
 * Created by joshoy on 16/4/17.
 */
public class ChatRoomClientSocketImpl extends ChatRoomClientSocket {

    protected ChatRoomSerializer cs;

    protected EncryptTypeEnum encryptType;

    protected Key connectionKey;

    /**
     * @param serverUri  WebSocket Server URI
     * @param dispatcher
     */
    public ChatRoomClientSocketImpl(URI serverUri, MessageDispatcher dispatcher, EncryptTypeEnum encryptType) {
        super(serverUri, dispatcher);
        this.cs = new ChatRoomSerializerImpl();
        this.encryptType = encryptType;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        // TODO: on server connected
        System.out.println("Starting connection, handshake info:" + serverHandshake.getHttpStatus());
    }

    @Override
    public void onMessage(String serverMsg) {
        String encryptedMsg = serverMsg;
        String decryptedMsg = null;

        // 加密模块有些问题, 暂时不用了
        decryptedMsg = encryptedMsg;

        BaseMessage msg = null;
        try {
            msg = (BaseMessage) cs.deserializeStringifyObject(decryptedMsg);
            this.dispatcher.dispatch(msg, this.getConnection());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO异常");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("反序列化失败");
        }
    }

    @Override
    public void onClose(int code, String reason, boolean closeByRemote) {
        // TODO
        System.out.println("Connection closed. Reason: " + reason + "; Closed by " + (closeByRemote ? "remote" : "local") + ".");
    }

    @Override
    public void onError(Exception e) {
        // TODO
        e.printStackTrace();
    }

    @Override
    public void setEncryptType(EncryptTypeEnum type) {
        this.encryptType = type;
    }

    @Override
    public void setConnectionKey(Key k) {
        this.connectionKey = k;
    }
}
