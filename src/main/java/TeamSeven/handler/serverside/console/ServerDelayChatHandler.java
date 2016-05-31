package TeamSeven.handler.serverside.console;

import TeamSeven.common.entity.Account;
import TeamSeven.common.entity.Session;
import TeamSeven.common.message.server.ServerDelayChatMessage;
import TeamSeven.handler.BaseHandler;
import TeamSeven.server.ChatRoomServerConsole;
import org.java_websocket.WebSocket;

import java.util.Date;

/**
 * Created by zhao on 2016/5/31.
 */
public class ServerDelayChatHandler extends BaseHandler {

    private ServerDelayChatMessage msg;
    private ChatRoomServerConsole chatRoomServerConsole;

    public ServerDelayChatHandler(ServerDelayChatMessage message, WebSocket conn, Object applier){
        super(conn);
        this.msg = message;
        this.chatRoomServerConsole = (ChatRoomServerConsole) applier;
    }

    public void onHandle() {
        Session currentSession = new Session(this.ws);
        Account sendAccount = chatRoomServerConsole.getAccountManager().getAccountBySession(currentSession);
        chatRoomServerConsole.consoleAppendLine(sendAccount.getUserId(),this.msg.getContent());
        chatRoomServerConsole.sendMessageToClient(
                this.ws,new ServerDelayChatMessage(this.msg.getContent()));
    }

    public ServerDelayChatMessage getMsg() {
        return msg;
    }

    public void setMsg(ServerDelayChatMessage msg) {
        this.msg = msg;
    }

    public ChatRoomServerConsole getChatRoomServerConsole() {
        return chatRoomServerConsole;
    }

    public void setChatRoomServerConsole(ChatRoomServerConsole chatRoomServerConsole) {
        this.chatRoomServerConsole = chatRoomServerConsole;
    }
}
