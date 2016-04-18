package TeamSeven.common.entity;

/**
 * Created by joshoy on 16/4/18.
 */
public final class Account {

    private String userId;     // 用户ID
    private String password;   // 密码

    public Account(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Account(Account account) {
        this.userId = account.getUserId();
        this.password = account.getPassword();
    }

    public String getUserId() {
        return this.userId;
    }

    public String getPassword() {
        return this.password;
    }
}
