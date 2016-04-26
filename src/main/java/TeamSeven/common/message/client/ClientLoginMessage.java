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

    public ClientLoginMessage(Account loginAccount) {
        this.loginAccount = loginAccount;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return null;
    }

    public Account getLoginAccount() {
        return this.loginAccount;
    }
}
