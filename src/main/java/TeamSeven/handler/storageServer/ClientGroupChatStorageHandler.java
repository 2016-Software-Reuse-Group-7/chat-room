package TeamSeven.handler.storageServer;

import TeamSeven.common.entity.Account;
import TeamSeven.common.entity.Session;
import TeamSeven.common.message.client.ClientGroupChatMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.storageServer.StorageServer;
import org.java_websocket.WebSocket;

/**
 * Created by tina on 16/5/31.
 */
public class ClientGroupChatStorageHandler extends BaseHandler {

    /**
     * 群组聊天的 message 对象
     */
    private ClientGroupChatMessage message;
    private StorageServer server;

    /**
     * 构建函数
     * @param message
     * @param webSocket
     * @param applier
     */
    public ClientGroupChatStorageHandler(ClientGroupChatMessage message, WebSocket webSocket, Object applier){
        super(webSocket);
        this.message = message;
        this.server = (StorageServer) applier;
    }

    public void onHandle() {
        Session currentSession = new Session(this.ws);
        Account senderAccount = server.getAccountManager().getAccountBySession(currentSession);

        /* Performance manager actions */
        this.server.getPerformanceManager().addReceivedMessage();

        /* Zip manager actions -- message record */
        try {
            this.server.groupMessageRecord(senderAccount.getUserId(), this.message.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* send to trans server */
        server.sendMessageToTransServer(this.message);
    }
}

