package TeamSeven.common.enumerate;

/**
 * Created by joshoy on 16/4/17.
 */
public enum EncryptTypeEnum {

    AES(true),
    RSA(false),
    Base64(true);

    private boolean isSymmetric;

    private EncryptTypeEnum(boolean isSymmetric) {
        this.isSymmetric = isSymmetric;
    }

    public boolean isSymmetricEncryption() {
        return this.isSymmetric;
    }
}
