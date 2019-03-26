package org.athena.common.util;

import org.athena.common.util.crypto.CommonUtil;
import org.athena.common.util.crypto.RSAUtil;
import org.jose4j.jwt.JwtClaims;
import org.junit.Test;

import java.security.KeyPair;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CryptoUtilTest {

    @Test
    public void timeTest() {
        LocalDate startDate = TimeUtil.parseLocalDate("2019-01-01");
        LocalDate endDate = TimeUtil.parseLocalDate("2020-01-01");

        long days = endDate.toEpochDay() - startDate.toEpochDay();
        long nowDays = LocalDate.now().toEpochDay() - startDate.toEpochDay();

        System.out.println("乙亥年一共 " + days + " 天");
        System.out.println("乙亥年已过 " + nowDays + " 天");
        double n = nowDays * 1D / days;
        System.out.println("乙亥年已过百分比 " + (n * 100) + "%");

    }

    @Test
    public void getUUID() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 30; i++) {
            executor.execute(() -> System.out.println(Thread.currentThread().getName() + ": \t" + CommonUtil.getUUID()));
        }

        executor.shutdown();
        executor.awaitTermination(60L, TimeUnit.SECONDS);

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

        String publicMi = RSAUtil.encryptByPublicKey(str, publicKey);
        System.out.println("公钥加密：\t" + publicMi);
        System.out.println("私钥解密：\t" + RSAUtil.decryptByPrivateKey(publicMi, privateKey));

    }

    @Test
    public void testJWT() throws Exception {
        String str = "subject";

        String token = JWTUtil.createToken(str, 120L);
        System.out.println("JWT token:\t" + token);

        JwtClaims claims = JWTUtil.validation(token, 120);
        System.out.println("JwtClaims:\t" + claims.toJson());

    }

    @Test
    public void testBCrypt() {
        String password = "subject";

        String hash = CommonUtil.hashpw(password);
        System.out.println("BCrypt 加密密文:\t" + hash);

        System.out.println("BCrypt 密文验证结果:\t" + CommonUtil.checkpw(password, hash));

    }

}
