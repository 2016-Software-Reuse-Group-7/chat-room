package TeamSeven.common.message.server;

import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

import java.security.PublicKey;

/**
 * Created by joshoy on 16/4/18.
 */
public class ServerRSAPubkey extends BaseMessage {
    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.SERVER_PUBKEY;
    }

    public ServerRSAPubkey(PublicKey pubKey) {
        this.pubKey = pubKey;
    }

    protected PublicKey pubKey;

    public PublicKey getServerPubKey() {
        return this.pubKey;
    }
}
