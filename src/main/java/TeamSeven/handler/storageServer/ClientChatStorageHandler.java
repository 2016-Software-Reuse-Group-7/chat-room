package TeamSeven.handler.storageServer;

import TeamSeven.common.entity.Account;
import TeamSeven.common.entity.Session;
import TeamSeven.common.message.client.ClientChatMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.storageServer.StorageServer;
import org.java_websocket.WebSocket;

/**
 * Created by tina on 16/5/31.
 */
public class ClientChatStorageHandler extends BaseHandler {

    private StorageServer server;
    private ClientChatMessage message;

    public ClientChatStorageHandler(ClientChatMessage msg, WebSocket conn, Object applier) {
        super(conn);
        this.message = msg;
        this.server = (StorageServer) applier;
    }

    @Override
    public void onHandle() {
        Session currentSession = new Session(this.ws);
        Account senderAccount = server.getAccountManager().getAccountBySession(currentSession);

        /* Performance manager actions */
        this.server.getPerformanceManager().addReceivedMessage();

        /* Zip manager actions -- message record */
        try {
            this.server.messageRecord(senderAccount.getUserId(), this.message.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* send to trans server */
        server.sendMessageToTransServer(this.message);
    }
}