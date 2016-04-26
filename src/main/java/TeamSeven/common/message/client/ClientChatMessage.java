package TeamSeven.common.message.client;

import TeamSeven.common.entity.Account;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

import java.security.Key;

/**
 * Created by joshoy on 16/4/18.
 */
public class ClientChatMessage extends BaseMessage {

    protected boolean isPrivate;
    protected String targetUserId;
    protected String content;

    public ClientChatMessage(String content) {
        this.isPrivate = false;
        this.content = content;
    }

    public ClientChatMessage(String content, String targetUserId) {
        this.isPrivate = true;
        this.content = content;
        this.targetUserId = targetUserId;
    }

    public String getContent() {
        return this.content;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.CLIENT_CHAT;
    }
}
