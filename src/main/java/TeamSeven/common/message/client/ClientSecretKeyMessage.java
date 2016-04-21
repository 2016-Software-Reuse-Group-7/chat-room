package TeamSeven.common.message.client;

import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

import java.security.Key;

/**
 * Created by joshoy on 16/4/21.
 */
public class ClientSecretKeyMessage extends BaseMessage {

    protected Key secretKey;

    public ClientSecretKeyMessage(Key secretKey) {
        this.secretKey = secretKey;
    }

    public Key getSecretKey() {
        return this.secretKey;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.CLIENT_SECKEY;
    }
}
