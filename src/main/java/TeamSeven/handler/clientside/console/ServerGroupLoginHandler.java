package TeamSeven.handler.clientside.console;

import TeamSeven.client.ChatRoomClientConsole;
import TeamSeven.common.entity.Account;
import TeamSeven.common.message.server.GroupMemberLoginMessage;
import TeamSeven.handler.BaseHandler;
import org.java_websocket.WebSocket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhao on 2016/5/17.
 */
public class ServerGroupLoginHandler extends BaseHandler {

    protected ChatRoomClientConsole chatRoomClientConsole;
    protected GroupMemberLoginMessage msg;

    public ServerGroupLoginHandler(GroupMemberLoginMessage msg ,WebSocket conn, Object applier) {
        super(conn);
        this.msg = msg;
        this.chatRoomClientConsole = (ChatRoomClientConsole) applier;
    }

    public void onHandle() {
        System.out.println("Some Group Member Logins in the chat-room");
        Account account = msg.getAccount();
        /**
         * 遍历好友列表 进行输出
         */
        HashMap<String,Boolean> groupList = chatRoomClientConsole.getGroupList();
        Map map = new HashMap();
        Iterator it = map.entrySet().iterator();
        /**
         * 根据传来的GroupMemberLoginMessage  对列表中的登陆情况进行维护
         */
        while (it.hasNext()){
            Map.Entry<String,Boolean> entry = (Map.Entry<String, Boolean>) it.next();
            if(entry.getKey() == account.getUserId()){
                entry.setValue(account.getStatus());
            }
            System.out.println(entry.getKey() + chatRoomClientConsole.convert(entry.getValue()));
        }
    }
}
