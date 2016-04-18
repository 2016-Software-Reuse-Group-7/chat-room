package TeamSeven.util.encrypt.RSA;

import TeamSeven.util.encrypt.AsymmertricCoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by joshoy on 16/4/18.
 */
public class RSACoder extends AsymmertricCoder {

    public void RSACoder() {
        this.RSACoder(true);
    }

    public void RSACoder(boolean spanNewPair) {
        if (spanNewPair) {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = null;
            try {
                keyPairGen = KeyPairGenerator.getInstance("RSA");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            //初始化密钥对生成器，密钥大小为1024位
            keyPairGen.initialize(1024);
            //生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            //得到私钥
            RSAPrivateKey privateK = (RSAPrivateKey)keyPair.getPrivate();
            //得到公钥
            RSAPublicKey publicK = (RSAPublicKey)keyPair.getPublic();

            this.setPublicKey(publicK);
            this.setPrivateKey(privateK);
        }
    }

    @Override
    public void setPublicKey(PublicKey pubKey) {
        this.publicKey = pubKey;
    }

    @Override
    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    @Override
    public void setPrivateKey(PrivateKey priKey) {
        this.privateKey = priKey;
    }

    protected PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    @Override
    public byte[] encryptWithPublicKey(byte[] srcBytes)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        RSAPublicKey pk = (RSAPublicKey)this.getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pk);
        byte[] resultBytes = cipher.doFinal(srcBytes);
        return resultBytes;
    }

    @Override
    public byte[] decryptWithPublicKey(byte[] srcBytes)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        RSAPublicKey pk = (RSAPublicKey)this.getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pk);
        byte[] resultBytes = cipher.doFinal(srcBytes);
        return resultBytes;
    }

    @Override
    public byte[] encryptWithPrivateKey(byte[] srcBytes)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        RSAPrivateKey pk = (RSAPrivateKey)this.getPrivateKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pk);
        byte[] resultBytes = cipher.doFinal(srcBytes);
        return resultBytes;
    }

    @Override
    public byte[] decryptWithPrivateKey(byte[] srcBytes)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        RSAPrivateKey pk = (RSAPrivateKey)this.getPrivateKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pk);
        byte[] resultBytes = cipher.doFinal(srcBytes);
        return resultBytes;
    }
}
