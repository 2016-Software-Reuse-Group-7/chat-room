package TeamSeven.util.encrypt;


import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * Created by joshoy on 16/4/18.
 */


public abstract class SymmetricCoder {

    public SymmetricCoder(String eType) throws NoSuchAlgorithmException, NoSuchPaddingException {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        //实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
        keygen = KeyGenerator.getInstance(eType);//
        // 生成密钥
        deskey = keygen.generateKey();
        // 生成Cipher对象,指定其支持的DES算法
        c = Cipher.getInstance(eType);
    }

    /**
     * KeyGenerator 提供对称密钥生成器的功能，支持各种算法
     */
    protected KeyGenerator keygen;

    /**
     * SecretKey 负责保存对称密钥
     */
    protected SecretKey deskey;

    /**
     * Cipher负责完成加密或解密工作
     */
    protected Cipher c;

    /**
     * 该字节数组负责保存加密的结果
     */
    protected byte[] cipherByte;

    /* 加密 */
    public abstract byte[] encrypt(String buff, Key usingKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    /* 解密 */
    public abstract byte[] decrypt(byte[] buff, Key usingKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    /* 产生并保存新密钥 */
    public abstract void spanKey();
    public abstract void spanKey(int keyLen);

}
