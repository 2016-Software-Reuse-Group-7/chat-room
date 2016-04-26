package TeamSeven.handler.clientside.console;

import TeamSeven.client.ChatRoomClientConsole;
import TeamSeven.common.entity.Chat;
import TeamSeven.common.message.client.ClientChatMessage;
import TeamSeven.common.message.server.ServerRespLoginSuccessMessage;
import TeamSeven.handler.BaseHandler;
import org.java_websocket.WebSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by joshoy on 16/4/26.
 */
public class ServerRespLoginSuccessHandler extends BaseHandler {

    protected ChatRoomClientConsole clientConsole;

    public ServerRespLoginSuccessHandler(ServerRespLoginSuccessMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.clientConsole = (ChatRoomClientConsole) applier;
    }

    @Override
    public void onHandle() {
        try {
            while (true) {
                BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
                String chatContent = sysin.readLine();

                if (chatContent.equals("exit")) {
                    // TODO: 退出
                }
                else {
                    ClientChatMessage chatMessage = new ClientChatMessage(chatContent);
                    this.clientConsole.sendMessageWithEncryption(chatMessage);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
