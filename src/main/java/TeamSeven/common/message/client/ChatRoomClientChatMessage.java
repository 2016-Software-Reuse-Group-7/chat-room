package TeamSeven.common.message.client;

import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.ChatRoomBaseMessage;

/**
 * Created by joshoy on 16/4/18.
 */
public class ChatRoomClientChatMessage extends ChatRoomBaseMessage {

    protected String content;

    public ChatRoomClientChatMessage() {
        this.content = null;
    }

    public ChatRoomClientChatMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.CLIENT_CHAT;
    }
}
