package TeamSeven.util.encrypt.AES;

import TeamSeven.util.encrypt.SymmetricCoder;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by joshoy on 16/4/18.
 */
public class AESCoder extends SymmetricCoder {

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public AESCoder() throws NoSuchAlgorithmException, NoSuchPaddingException {
        super(KEY_ALGORITHM);
    }

    /**
     * 对字符串加密
     *
     * @param str
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    @Override
    public byte[] encrypt(String str, Key usingKey) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        // 判断是否使用了自定义密钥
        if (null == usingKey) {
            usingKey = deskey;
        }
        // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
        c.init(Cipher.ENCRYPT_MODE, usingKey);
        byte[] src = str.getBytes();
        // 加密，结果保存进cipherByte
        cipherByte = c.doFinal(src);
        return cipherByte;
    }

    /**
     * 对字符串解密
     *
     * @param buff
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    @Override
    public byte[] decrypt(byte[] buff, Key usingKey) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        // 判断是否使用了自定义密钥
        if (null == usingKey) {
            usingKey = deskey;
        }
        // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
        c.init(Cipher.DECRYPT_MODE, usingKey);
        cipherByte = c.doFinal(buff);
        return cipherByte;
    }

    @Override
    public SecretKey spanKey() {
        // 默认为256位密钥
        return this.spanKey(256);
    }

    @Override
    public SecretKey spanKey(int keyLen) {
        try {
            this.keygen = KeyGenerator.getInstance(this.KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // keygen.init(keyLen); // for example
        SecretKey secretKey = keygen.generateKey();
        System.out.println("New key: " + secretKey.toString());
        this.deskey = secretKey;
        return secretKey;
    }

}
