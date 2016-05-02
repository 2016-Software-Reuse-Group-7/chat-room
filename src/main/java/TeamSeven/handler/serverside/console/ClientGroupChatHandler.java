package TeamSeven.handler.serverside.console;

import TeamSeven.common.entity.Account;
import TeamSeven.common.entity.Session;
import TeamSeven.common.exception.ChatRoomBaseException;
import TeamSeven.common.message.client.ClientChatMessage;
import TeamSeven.common.message.client.ClientGroupChatMessage;
import TeamSeven.common.message.server.ServerTransferChatMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.ChatRoomServerConsole;
import org.java_websocket.WebSocket;
import java.lang.Object;
import java.util.Date;

/**
 * 为群组聊天新建一种handler 用来专门处理GroupChat 类型的信息
 * Created by zhao on 2016/5/2.
 */
public class ClientGroupChatHandler extends BaseHandler {

    /**
     * 群组聊天的 message 对象
     */
    private ClientGroupChatMessage message;
    private ChatRoomServerConsole serverConsole;

    /**
     * 构建函数
     * @param message
     * @param webSocket
     * @param applier
     */
    public ClientGroupChatHandler(ClientGroupChatMessage message, WebSocket webSocket, Object applier){
        super(webSocket);
        this.message = message;
        this.serverConsole = (ChatRoomServerConsole) applier;
    }

    /**
     * 对群组消息的处理  通过群组Id 发送信息到对应的组里面去
     */
    public void onHandle() {
        Session currentSession = new Session(this.ws);
        Account senderAccount = serverConsole.getAccountManager().getAccountBySession(currentSession);
        serverConsole.consoleAppendLine(senderAccount.getUserId(),this.message.getContent());
        serverConsole.sendMessageToGroup(
                new ServerTransferChatMessage(this.message.getContent(),senderAccount.getUserId(),new Date())
        );
        this.serverConsole.getPerformanceManager().addReceivedMessage();
    }
}
