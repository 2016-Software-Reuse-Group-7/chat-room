package TeamSeven.server.account;

import TeamSeven.common.entity.Account;
import TeamSeven.common.enumerate.AccountVerifyResultEnum;

import java.util.Collection;

/**
 * Created by joshoy on 16/4/18.
 */
public abstract class AccountManager {

    protected Collection<Account> loginAccountSet;

    public AccountManager() {}

    public abstract boolean hasLogged(Account account);

    public abstract boolean accountLogin(Account account);

    public abstract AccountVerifyResultEnum verifyAccount(Account account);
}
