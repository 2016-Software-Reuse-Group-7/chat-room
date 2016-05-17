package TeamSeven.common.entity;

import java.io.Serializable;

/**
 * Created by joshoy on 16/4/18.
 */
public final class Account implements Serializable {

    private String userId;     // 用户ID
    private String password;   // 密码
    private Boolean status;   //登陆状态

    public Account(String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.status = false;
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

    @Override
    public int hashCode() {
        return (this.userId.hashCode() + "::" + this.password.hashCode()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if ( !(obj instanceof Account) ) {
            return false;
        }
        Account o = (Account) obj;
        if ( this.userId.equals(o.getUserId()) && this.password.equals(o.getPassword()) ) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
