package TeamSeven.common.message.client;

import TeamSeven.common.entity.Account;
import TeamSeven.common.enumerate.EncryptTypeEnum;
import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

/**
 * Created by joshoy on 16/4/19.
 */
public class ClientLoginMessage extends BaseMessage {

    protected Account loginAccount;
    EncryptTypeEnum encryptType;

    public ClientLoginMessage(Account loginAccount, EncryptTypeEnum encryptType) {
        this.loginAccount = loginAccount;
        this.encryptType = encryptType;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return null;
    }
}
