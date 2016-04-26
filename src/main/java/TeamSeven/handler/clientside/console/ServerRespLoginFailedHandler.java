package TeamSeven.handler.clientside.console;

import TeamSeven.client.ChatRoomClientConsole;
import TeamSeven.common.entity.Account;
import TeamSeven.common.message.client.ClientLoginMessage;
import TeamSeven.common.message.server.ServerAskLoginMessage;
import TeamSeven.common.message.server.ServerRespLoginFailedMessage;
import TeamSeven.handler.BaseHandler;
import org.java_websocket.WebSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by joshoy on 16/4/26.
 */
public class ServerRespLoginFailedHandler extends BaseHandler {

    protected ServerRespLoginFailedMessage message;
    protected ChatRoomClientConsole clientConsole;

    public ServerRespLoginFailedHandler(ServerRespLoginFailedMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.message = msg;
        this.clientConsole = (ChatRoomClientConsole) applier;
    }

    @Override
    public void onHandle() {
        System.out.println("登录失败, 请重新登录.");
        clientConsole.selfDispatch(new ServerAskLoginMessage());
    }
}
