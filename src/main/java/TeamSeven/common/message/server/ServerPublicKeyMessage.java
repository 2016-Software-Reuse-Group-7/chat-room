package TeamSeven.common.message.server;

import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

import java.security.PublicKey;

/**
 * Created by joshoy on 16/4/19.
 */
public class ServerPublicKeyMessage extends BaseMessage {

    protected PublicKey key;

    public ServerPublicKeyMessage(PublicKey key) {
        this.key = key;
    }

    public PublicKey getServerPublicKey() {
        return this.key;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.SERVER_PUBKEY;
    }

}
