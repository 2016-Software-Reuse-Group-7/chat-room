package TeamSeven.common.message.client;

import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

import java.security.Key;
import java.security.PublicKey;

/**
 * Created by joshoy on 16/4/25.
 */
public class ClientRespEncryptTypeMessage extends BaseMessage {

    protected Key clientKey;
    protected EncryptTypeEnum encryptType;

    public ClientRespEncryptTypeMessage(EncryptTypeEnum eType, Key clientKey) {
        this.encryptType = eType;
        this.clientKey = clientKey;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return null;
    }

    public Key getClientKey() {
        return this.clientKey;
    }

    public EncryptTypeEnum getEncryptType() {
        return this.encryptType;
    }
}
