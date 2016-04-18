package TeamSeven.common.message.server;

import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.ChatRoomBaseMessage;

/**
 * Created by joshoy on 16/4/18.
 */
public class ChatRoomServerBoardcastMessage extends ChatRoomBaseMessage {

    public String getContent() {
        return content;
    }

    protected String content;

    public ChatRoomServerBoardcastMessage(String content) {
        this.content = content;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.SERVER_BOARDCAST;
    }
}
