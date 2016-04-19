package TeamSeven.client;

import TeamSeven.client.socket.ChatRoomClientSocket;

import java.security.Key;
import java.security.PublicKey;

/**
 * Created by joshoy on 16/4/17.
 */
public class ChatRoomClientConsole {

    /* Client WebSocket */
    protected ChatRoomClientSocket clientSocket;
    /* 与Server连接的公钥 */
    protected PublicKey pubKey;

    public ChatRoomClientConsole() {

    }

    /* *
     *  +------------------+
     *  | API for handlers |
     *  +------------------+
     * */

    public void setServerPublicKey(Key pubKey) {
        this.pubKey = (PublicKey) pubKey;
    }

}
