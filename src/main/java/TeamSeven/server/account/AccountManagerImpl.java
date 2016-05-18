package TeamSeven.server.account;

import TeamSeven.common.entity.Account;
import TeamSeven.common.entity.Session;
import TeamSeven.common.enumerate.AccountVerifyResultEnum;
import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by joshoy on 16/4/18.
 */
public class AccountManagerImpl extends AccountManager {

    public AccountManagerImpl() {
        super();
        this.loginAccountSet = new HashSet<Account>();
        this.accountMap = new HashMap<Session, Account>();
    }

    @Override
    public boolean hasLogged(Account account) {
        return this.loginAccountSet.contains(account);
    }

    @Override
    public boolean accountLogin(WebSocket conn, Account account) {
        if ( this.hasLogged(account) && verifyAccount(account).isValid() ) {
            return false;
        }
        else {
            this.loginAccountSet.add(account);
            this.accountMap.put(new Session(conn), account);
            LoginOrLogOut(account.getUserId(),true);
            return true;
        }
    }

    @Override
    public AccountVerifyResultEnum verifyAccount(Account account) {
        // TODO: Database access and verification
        return AccountVerifyResultEnum.OK;
    }

    @Override
    public Account getAccountBySession(Session session) {
        return this.accountMap.get(session);
    }

    @Override
    public List<Account> getAccountByGroupId(Long groupId) {
        List<Account> list = new ArrayList<Account>();
        list.add(this.accountMap.get(groupId));
        return list;
    }

}
