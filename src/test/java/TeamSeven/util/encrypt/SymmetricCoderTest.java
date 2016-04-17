package TeamSeven.util.encrypt;

import TeamSeven.util.encrypt.AES.AESCoder;
import org.junit.Test;

/**
 * Created by joshoy on 16/4/18.
 */
public class SymmetricCoderTest {



    @Test
    public void testEncryptAndDecrypt() throws Exception {

        SymmetricCoder sc = new AESCoder();
        sc.spanKey();

        String msg = "密文测试.";
        System.out.println("加密前: ");
        System.out.println(msg);
        System.out.println("");

        // encrypt
        byte[] encodedMsg = sc.encrypt(msg, null);
        System.out.println("加密后: ");
        System.out.println(encodedMsg.toString());
        System.out.println("");

        // decrypt
        byte[] decodedMsg = sc.decrypt(encodedMsg, null);
        System.out.println("解密后: ");
        System.out.println( new String(decodedMsg) );
        System.out.println("");
    }
}