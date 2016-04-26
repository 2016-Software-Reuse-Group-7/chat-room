package TeamSeven.handler.serverside.console;

import TeamSeven.common.entity.Account;
import TeamSeven.common.entity.Session;
import TeamSeven.common.message.client.ClientChatMessage;
import TeamSeven.common.message.server.ServerTransferChatMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.ChatRoomServerConsole;
import org.java_websocket.WebSocket;

import java.util.Date;

/**
 * Created by joshoy on 16/4/18.
 */
public class ClientChatHandler extends BaseHandler {

    private ClientChatMessage message;
    private ChatRoomServerConsole serverConsole;

    public ClientChatHandler(ClientChatMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.message = msg;
        this.serverConsole = (ChatRoomServerConsole) applier;
    }

    @Override
    public void onHandle() {
        Session currentSession = new Session(this.ws);
        Account senderAccount = serverConsole.getAccountManager().getAccountBySession(currentSession);
        serverConsole.consoleAppendLine( senderAccount.getUserId(), this.message.getContent() );
        serverConsole.sendMessageToAll(
                new ServerTransferChatMessage( this.message.getContent(), senderAccount.getUserId(), new Date() )
        );
    }
}