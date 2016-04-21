package TeamSeven.handler.server.console;

import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.message.server.ServerAskEncrypyTypeMessage;
import TeamSeven.common.message.server.ServerPublicKeyMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.ChatRoomServerConsole;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/21.
 */
public class ServerAskEncryptTypeHandler extends BaseHandler {

    protected ChatRoomServerConsole serverConsole;

    public ServerAskEncryptTypeHandler(ServerAskEncrypyTypeMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.serverConsole = (ChatRoomServerConsole) applier;
    }

    @Override
    public void onHandle() {
        System.out.println("Server asking encryption type for: " + this.ws.getRemoteSocketAddress().toString());
        this.serverConsole.setConnectionKeyAndEncryptType(this.ws, this.serverConsole.getDefaultPrivateKey(), EncryptTypeEnum.RSA);
        this.serverConsole.serializeMessageToString(new ServerAskEncrypyTypeMessage(this.serverConsole.getDefaultPublicKey()));
    }
}
