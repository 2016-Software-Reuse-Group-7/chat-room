package TeamSeven.common.message.server;

import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

import java.util.Date;

/**
 * Created by joshoy on 16/4/26.
 */
public class ServerTransferChatMessage extends BaseMessage {

    public String getContent() {
        return content;
    }

    protected String content;

    public Date getChatTime() {
        return chatTime;
    }

    protected Date chatTime;

    public String getSenderId() {
        return senderId;
    }

    protected String senderId;

    public ServerTransferChatMessage(String content, String senderId, Date chatTime) {
        this.content = content;
        this.senderId = senderId;
        this.chatTime = chatTime;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.SERVER_TRANSFER_CHAT;
    }
}
