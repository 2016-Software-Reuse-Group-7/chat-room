package TeamSeven.handler.serverside.console;

import TeamSeven.common.message.client.ClientLoginMessage;
import TeamSeven.common.message.server.GroupMemberLoginMessage;
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
        this.serverConsole = (ChatRoomServerConsole) applier;
    }

    @Override
    public void onHandle() {
        boolean success = this.serverConsole.clientLogin(this.ws, message.getLoginAccount());
        /**
         * 更改用户的登陆状态
         */
        message.getLoginAccount().setStatus(true);
        if (success) {
            serverConsole.sendMessageToClient( this.ws, new ServerRespLoginSuccessMessage() );
            /**
             * 以前只需要将登陆成功的信息发送给登陆者  现在还需要把Member登陆成功的信息 发送给group中的成员。
             */
            serverConsole.sendMessageToGroup(new GroupMemberLoginMessage(message.getLoginAccount()));
        }
        else {
            serverConsole.sendMessageToClient( this.ws, new ServerRespLoginFailedMessage() );
        }
    }
}
