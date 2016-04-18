package TeamSeven.common.enumerate;

/**
 * Created by joshoy on 16/4/18.
 */
public enum AccountVerifyResultEnum {
    OK(true),
    PASSWORD_ERROR(false),
    USERID_NOT_FOUND(false);

    protected boolean validResult;

    AccountVerifyResultEnum(boolean valid) {
        this.validResult = valid;
    }

    public boolean isValid() {
        return this.validResult;
    }
}
