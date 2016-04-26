package TeamSeven.handler.clientside.console;

import TeamSeven.client.ChatRoomClientConsole;
import TeamSeven.common.entity.Account;
import TeamSeven.common.message.client.ClientLoginMessage;
import TeamSeven.common.message.server.ServerAskLoginMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.ChatRoomServerConsole;
import org.java_websocket.WebSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by joshoy on 16/4/26.
 */
public class ServerAskLoginHandler extends BaseHandler {

    protected ServerAskLoginMessage message;
    protected ChatRoomClientConsole clientConsole;

    public ServerAskLoginHandler(ServerAskLoginMessage message, WebSocket conn, Object applier) {
        super(conn);
        this.message = message;
        this.clientConsole = (ChatRoomClientConsole) applier;
    }

    @Override
    public void onHandle() {
         // Server要求Client进行登录
        System.out.println("Server要求Client进行登录.");

        /* 如果已经登录 */
        if ( this.clientConsole.getLogged() ) {
            // TODO: Disconnect
        }

        Account account = this.clientConsole.UserEnterAccount();
        this.clientConsole.sendMessageWithEncryption(new ClientLoginMessage(account));
    }
}
