package org.athena.util;

import org.junit.Test;

import java.security.KeyPair;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CryptoUtilTest {

    @Test
    public void getUUID() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 1000; i++) {
            executor.execute(() -> System.out.println(Thread.currentThread().getName() + ": \t" + CryptoUtil.getUUID()));
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

}
