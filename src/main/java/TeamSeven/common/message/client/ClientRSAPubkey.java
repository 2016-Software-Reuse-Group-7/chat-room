package TeamSeven.common.message.client;

import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

import java.security.PublicKey;

/**
 * Created by joshoy on 16/4/18.
 */
public class ClientRSAPubkey extends BaseMessage {
    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.CLIENT_PUBKEY;
    }

    protected PublicKey pubKey;

    public ClientRSAPubkey(PublicKey pubKey) {
        this.pubKey = pubKey;
    }

    public PublicKey getClientPubKey() {
        return this.pubKey;
    }
}
