package TeamSeven.common.enumerate;

import TeamSeven.util.encrypt.AES.AESCoder;
import TeamSeven.util.encrypt.Base64.Base64Coder;
import TeamSeven.util.encrypt.RSA.RSACoder;

/**
 * Created by joshoy on 16/4/17.
 */
public enum EncryptTypeEnum {

    AES(true, AESCoder.class),
    RSA(false, RSACoder.class),
    Base64(true, Base64Coder.class);

    private boolean isSymmetric;
    private Class coderClass;

    private EncryptTypeEnum(boolean isSymmetric, Class Coder) {
        this.isSymmetric = isSymmetric;
        this.coderClass = Coder;
    }

    public boolean isSymmetricEncryption() {
        return this.isSymmetric;
    }

    public Class getCoderClass() {
        return this.coderClass;
    }
}
