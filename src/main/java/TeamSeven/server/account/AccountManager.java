package TeamSeven.server.account;

import TeamSeven.common.entity.Account;
import TeamSeven.common.entity.Session;
import TeamSeven.common.enumerate.AccountVerifyResultEnum;
import org.java_websocket.WebSocket;

import java.util.Collection;
import java.util.Map;

/**
 * Created by joshoy on 16/4/18.
 */
public abstract class AccountManager {

    protected Collection<Account> loginAccountSet;

    protected Map<Session, Account> accountMap;

    public AccountManager() {}

    public abstract boolean hasLogged(Account account);

    public abstract boolean accountLogin(WebSocket conn, Account account);

    public abstract AccountVerifyResultEnum verifyAccount(Account account);

    public abstract Account getAccountBySession(Session session);
}
