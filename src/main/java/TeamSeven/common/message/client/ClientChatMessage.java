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
    protected String content;
    protected EncryptTypeEnum encryptType;

    public ClientChatMessage(String content, Account sender) {
        this.sender = sender;
        this.content = content;
        this.encryptType = null;
    }

    public String getContent() {
        return this.content;
    }

    public Account getAccount() { return this.sender; }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.CLIENT_CHAT;
    }
}
