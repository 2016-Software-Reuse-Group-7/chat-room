package TeamSeven.common.message.server;

import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

import java.security.PublicKey;

/**
 * Created by joshoy on 16/4/21.
 */
public class ServerAskEncrypyTypeMessage extends BaseMessage {

    protected PublicKey publicKey;

    public ServerAskEncrypyTypeMessage() {

    }

    public ServerAskEncrypyTypeMessage(PublicKey k) {
        this.publicKey = k;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.SERVER_ASK_ENCRYPT_TYPE;
    }

    public PublicKey getServerPublicKey() {
        return this.publicKey;
    }
}
