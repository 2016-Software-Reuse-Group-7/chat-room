package TeamSeven.util.encrypt.Base64;

import TeamSeven.util.encrypt.SymmetricCoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by joshoy on 16/4/17.
 */

public class Base64Coder extends SymmetricCoder {

    public Base64Coder(String eType) throws NoSuchAlgorithmException, NoSuchPaddingException {
        super("");
    }

    /**
     * BASE64编码
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64解码
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    @Override
    public byte[] encrypt(String buff, Key usingKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            return this.encryptBASE64( buff.getBytes(StandardCharsets.UTF_8) )
                       .getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public byte[] decrypt(byte[] buff, Key usingKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            return this.decryptBASE64(new String(buff));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public SecretKey spanKey() {
        return null;
    }

    @Override
    public SecretKey spanKey(int keyLen) {
        return null;
    }
}
