package TeamSeven.util.encrypt;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
/**
 * Created by joshoy on 16/4/18.
 */


public abstract class AsymmertricCoder {

    protected PublicKey publicKey;
    protected PrivateKey privateKey;

    public abstract void setPublicKey(PublicKey pubKey);
    public abstract void setPrivateKey(PrivateKey priKey);
    public abstract PublicKey getPublicKey();

    /* 非对称公钥加密 */
    protected abstract byte[] encryptWithPublicKey(byte[] srcBytes)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException;

    /* 非对称公钥解密 */
    protected abstract byte[] decryptWithPublicKey(byte[] srcBytes)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException;

    /* 非对称私钥加密 */
    protected abstract byte[] encryptWithPrivateKey(byte[] srcBytes)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException;

    /* 非对称私钥解密 */
    protected abstract byte[] decryptWithPrivateKey(byte[] srcBytes)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException;

}
