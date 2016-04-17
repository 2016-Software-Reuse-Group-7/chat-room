package TeamSeven.util.encrypt.RSA;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 *
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public class RSACoderOldTest {
    private String publicKey;
    private String privateKey;

    @Before
    public void setUp() throws Exception {
        Map<String, Object> keyMap = RSACoderOld.initKey();

        publicKey = RSACoderOld.getPublicKey(keyMap);
        privateKey = RSACoderOld.getPrivateKey(keyMap);
        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);
    }

    @Test
    public void test() throws Exception {
        System.out.println("公钥加密——私钥解密");
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoderOld.encryptByPublicKey(data, publicKey);

        byte[] decodedData = RSACoderOld.decryptByPrivateKey(encodedData,
                privateKey);

        String outputStr = new String(decodedData);
        System.out.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);

    }

    @Test
    public void testSign() throws Exception {
        System.out.println("私钥加密——公钥解密");
        String inputStr = "sign";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoderOld.encryptByPrivateKey(data, privateKey);

        byte[] decodedData = RSACoderOld
                .decryptByPublicKey(encodedData, publicKey);

        String outputStr = new String(decodedData);
        System.out.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);

        System.out.println("私钥签名——公钥验证签名");
        // 产生签名  
        String sign = RSACoderOld.sign(encodedData, privateKey);
        System.out.println("签名:\r" + sign);

        // 验证签名  
        boolean status = RSACoderOld.verify(encodedData, publicKey, sign);
        System.err.println("状态:\r" + status);
        assertTrue(status);

    }

}  