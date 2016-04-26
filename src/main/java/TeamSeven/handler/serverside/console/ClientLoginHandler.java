package TeamSeven.handler.serverside.console;

import TeamSeven.common.message.client.ClientLoginMessage;
import TeamSeven.common.message.server.ServerRespLoginFailedMessage;
import TeamSeven.common.message.server.ServerRespLoginSuccessMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.ChatRoomServerConsole;
import org.java_websocket.WebSocket;

/**
 * Created by joshoy on 16/4/26.
 */
public class ClientLoginHandler extends BaseHandler {

    protected ClientLoginMessage message;
    protected ChatRoomServerConsole serverConsole;

    public ClientLoginHandler(ClientLoginMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.message = msg;
        logger.info(msg);
        this.serverConsole = (ChatRoomServerConsole) applier;
    }

    @Override
    public void onHandle() {
        boolean success = this.serverConsole.clientLogin(this.ws, message.getLoginAccount());
        if (success) {
            serverConsole.sendMessageToClient( this.ws, new ServerRespLoginSuccessMessage() );
        }
        else {
            serverConsole.sendMessageToClient( this.ws, new ServerRespLoginFailedMessage() );
        }
    }
}
