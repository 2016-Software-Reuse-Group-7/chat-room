package TeamSeven.common.message.client;

import TeamSeven.common.entity.Account;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;
import TeamSeven.common.message.IEncryptableMessage;

import java.security.Key;

/**
 * Created by joshoy on 16/4/18.
 */
public class ClientChatMessage extends BaseMessage implements IEncryptableMessage {

    protected Account sender;
    protected String content;
    protected boolean encrypted;

    public ClientChatMessage() {
        this.content = null;
    }

    public ClientChatMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public Account getAccount() { return this.sender; }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.CLIENT_CHAT;
    }


    public void encryptMessage(Key k) {

    }

    public void setEncryptedType(EncryptTypeEnum type) {

    }

    public EncryptTypeEnum getEncryptType() {
        return null;
    }
}
