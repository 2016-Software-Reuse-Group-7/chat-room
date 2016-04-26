package TeamSeven.handler.serverside.console;

import TeamSeven.common.entity.Session;
import TeamSeven.common.message.client.ClientRespEncryptTypeMessage;
import TeamSeven.common.message.server.ServerAskLoginMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.ChatRoomServerConsole;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/26.
 */
public class ClientRespEncryptTypeHandler extends BaseHandler {

    protected ClientRespEncryptTypeMessage message;
    protected ChatRoomServerConsole serverConsole;

    public ClientRespEncryptTypeHandler(ClientRespEncryptTypeMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.message = msg;
        this.serverConsole = (ChatRoomServerConsole) applier;
    }

    @Override
    public void onHandle() {
        serverConsole.setConnectionKeyAndEncryptType(this.ws, message.getClientKey(), message.getEncryptType());
        serverConsole.sendMessageToClient( this.ws, new ServerAskLoginMessage() );
    }
}
