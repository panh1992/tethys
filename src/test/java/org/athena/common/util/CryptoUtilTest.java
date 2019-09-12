package org.athena.common.util;

import org.athena.common.util.crypto.BCryptUtil;
import org.athena.common.util.crypto.RSAUtil;
import org.jose4j.jwt.JwtClaims;
import org.junit.Assert;
import org.junit.Test;

import java.security.KeyPair;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CryptoUtilTest {

    @Test
    public void getSnowflakeId() throws InterruptedException {

        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Vector<Long> vector = new Vector<>();
        int count = 500;
        for (int i = 0; i < count; i++) {
            executor.execute(() -> {
                long id = idWorker.nextId();
                vector.add(id);
                System.out.println(Thread.currentThread().getName() + ": \t" + id);
            });
        }

        executor.shutdown();
        executor.awaitTermination(60L, TimeUnit.SECONDS);

        Assert.assertEquals(vector.size(), count);

    }

    @Test
    public void testRSA() throws Exception {
        String str = "RSA 加密 测试数据 ～·`！@#¥%……&*（）!@#$%^&*(()。。";

        KeyPair keyPair = RSAUtil.initKeyPar();
        String privateKey = RSAUtil.getPrivateKey(keyPair);
        System.out.println("RSA 私钥：\t" + privateKey);
        String publicKey = RSAUtil.getPublicKey(keyPair);
        System.out.println("RSA 公钥：\t" + publicKey);

        String privateMi = RSAUtil.encryptByPrivateKey(str, privateKey);
        System.out.println("私钥加密：\t" + privateMi);
        System.out.println("公钥解密：\t" + RSAUtil.decryptByPublicKey(privateMi, publicKey));
        Assert.assertEquals(str, RSAUtil.decryptByPublicKey(privateMi, publicKey));

        String publicMi = RSAUtil.encryptByPublicKey(str, publicKey);
        System.out.println("公钥加密：\t" + publicMi);
        System.out.println("私钥解密：\t" + RSAUtil.decryptByPrivateKey(publicMi, privateKey));
        Assert.assertEquals(str, RSAUtil.decryptByPrivateKey(publicMi, privateKey));

    }

    @Test
    public void testJWT() throws Exception {
        String str = "subject";

        String token = JWTUtil.createToken(str, 120L);
        System.out.println("JWT token:\t" + token);

        JwtClaims claims = JWTUtil.validation(token, 120);
        System.out.println("JwtClaims:\t" + claims.toJson());

        Assert.assertEquals(str, claims.getSubject());

    }

    @Test
    public void testBCrypt() {
        String password = "subject";

        String hash = BCryptUtil.hashpw(password);
        System.out.println("BCrypt 加密密文:\t" + hash);

        System.out.println("BCrypt 密文验证结果:\t" + BCryptUtil.checkpw(password, hash));

        Assert.assertTrue(BCryptUtil.checkpw(password, hash));

    }

}
