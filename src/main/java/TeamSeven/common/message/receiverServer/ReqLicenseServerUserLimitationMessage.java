package TeamSeven.common.message.receiverServer;

import TeamSeven.common.entity.Account;
import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;

/**
 * Created by joshoy on 16/5/27.
 */
public class ReqLicenseServerUserLimitationMessage extends BaseMessage {

    protected Account account;

    ReqLicenseServerUserLimitationMessage(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.REQ_LICENSE_SERVER_USER_LIMITATION;
    }
}
