# 加密模块

本模块实现的加密方法: 
1. Base64 (严格来讲不算加密, 至少比裸数据强, 有时可以加速传输)
2. AES (对称加密)
3. RSA (推荐, 非对称加密, 最安全, 但是缺点是速度慢)
4. ...


## 使用方法

```java

public void testEncryptAndDecrypt() throws Exception {

        SymmetricCoder sc = new AESCoder();
        sc.spanKey();

        String msg = "密文测试.";
        System.out.println("加密前: ");
        System.out.println(msg);
        System.out.println("");

        // encrypt, 当key = null时, 使用sc的key.
        byte[] encodedMsg = sc.encrypt(msg, null);
        System.out.println("加密后: ");
        System.out.println(encodedMsg.toString());
        System.out.println("");

        // decrypt, 当key = null时, 使用sc的key.
        byte[] decodedMsg = sc.decrypt(encodedMsg, null);
        System.out.println("解密后: ");
        System.out.println( new String(decodedMsg) );
        System.out.println("");
}

```