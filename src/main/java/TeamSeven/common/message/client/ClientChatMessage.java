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

    protected Account sender;
    protected boolean isPrivate;
    protected String targetUserId;
    protected String content;

    public ClientChatMessage(String content, Account sender) {
        this.isPrivate = false;
        this.sender = sender;
        this.content = content;
    }

    public ClientChatMessage(String content, Account sender, String targetUserId) {
        this.isPrivate = true;
        this.sender = sender;
        this.content = content;
        this.targetUserId = targetUserId;
    }

    public String getContent() {
        return this.content;
    }

    public Account getAccount() {
        return this.sender;
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
