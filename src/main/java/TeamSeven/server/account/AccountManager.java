package TeamSeven.server.account;

import TeamSeven.common.entity.Account;
import TeamSeven.common.entity.Session;
import TeamSeven.common.enumerate.AccountVerifyResultEnum;
import org.java_websocket.WebSocket;

import java.util.*;

/**
 * Created by joshoy on 16/4/18.
 */
public abstract class AccountManager {

    protected Collection<Account> loginAccountSet;

    protected Map<Session, Account> accountMap;

    private List<HashMap<String,Boolean>> groupList;

    public AccountManager() {}

    public abstract boolean hasLogged(Account account);

    public abstract boolean accountLogin(WebSocket conn, Account account);

    public abstract AccountVerifyResultEnum verifyAccount(Account account);

    public abstract Account getAccountBySession(Session session);

    /**
     * 通过群组ID 获取当前群组的AccountList
     * @param groupId
     * @return
     */
    public abstract List<Account> getAccountByGroupId(Long groupId);

    public List<HashMap<String, Boolean>> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<HashMap<String, Boolean>> groupList) {
        this.groupList = groupList;
    }

    /**
     * 通过改变表中的值  对在线状态进行改变
     * @param accountName
     * @param status
     */
    public void LoginOrLogOut(String accountName, Boolean status){
        for( HashMap<String,Boolean> ls : groupList ){
            Map map = new HashMap();
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<String,Boolean> entry = (Map.Entry<String, Boolean>) it.next();
                if (entry.getKey().equals(accountName)){
                    entry.setValue(status);
                }
            }
        }
    }
}
