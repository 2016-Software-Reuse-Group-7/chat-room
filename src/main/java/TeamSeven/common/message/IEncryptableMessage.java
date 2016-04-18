package TeamSeven.common.message;

import TeamSeven.common.enumerate.EncryptTypeEnum;

import java.security.Key;
import java.security.PublicKey;

/**
 * Created by joshoy on 16/4/18.
 * 可加密信息接口
 */

public interface IEncryptableMessage {
    void encryptMessage(Key k);
    void setEncryptedType(EncryptTypeEnum type);
    EncryptTypeEnum getEncryptType();
}
