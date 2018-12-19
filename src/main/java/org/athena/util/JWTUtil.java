package org.athena.util;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * jwt 工具类
 */
public final class JWTUtil {

    private static Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    /**
     * 生成一个RSA密钥对，用于JWT的签名和验证
     */
    private static RSAPrivateKey rsaPrivateKey;

    private static RSAPublicKey rsaPublicKey;

    private static final String PRIVATE_KEY_ID = "岁月无声";

    private static final String PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDPGIcmCgrYIVmyTcgEGbvFH"
            + "TPUJGrBJPReg76nx13e4sWIVln4jAPzqZH5k+STM+K1DgL2qn5+i7IvWTWZPdRpvwEZK4k1hZUZB3CxDUjtZ/wgX481/7LmnBaNM9ciIt"
            + "Vub/US7lSHXckz6Al/Fo3BxlB9XCGZqFORBmS0zrN+L4MNmI1uV1oOYQ47WewvO7mRyffuhdfjNEaeDWO0nelEBI+b3p12bu9scv0wak3"
            + "4lULQRxc8vDKg154O35bh/EWarEWMvpW8cTjpBzflap8Kb57WVDv/1l/a09nl9kbgyayxwy3oA8AnfNpEmMBSR1DL0K/FVyj4+4lbBQEW"
            + "jLnrAgMBAAECggEBAIY4ZGcv/QVE4Chipu6Zawv+uDeW/qV9eO/EvQ1qdhldqh6JsLgYrvr9/MDfrDYHW7hyg1Mw/tivW2W6kLVn0EJU6"
            + "7ZPdBb/I7ncC5qEfK55RXwJ0vxxY5Z4tVkArz7NiryPzSte0eQF0cBVc8otaOSczk/hJVm8MaT9mslgag6uO9hhLBKkxg+zArthdCYtRE"
            + "9z8n13CGvckebeeVpeUpklmFrZY743B8U03rOd7GKQngjSwxCgwuIRrC3GgR+JcsqyhTAm5GvOJuQpdnbMrOCOE5Pkb4AEza3m5UtSmJ4"
            + "CfSuO7d7MtFJ1zJdPSt2rJv6wdTMVaLMLaV9INkKkCrkCgYEA7B5Efw8ETACfhM4EC/Y+p+lBL78pUHxhOi28ViPlwDiddWrqhMzse3YI"
            + "OqWzLP4I3cSXLQSi7AAedJIbVlLA0RaHYlJYubkNPN3vVlD1l0hq1xWp7qnw28SYtfDie61VzfYmv7apsPQ6sU6Uybx1WJybDffObcds9"
            + "0FaNeGa1acCgYEA4IioMokyCMoMIynRrMDtPSti9mFK6AvjUSzE2AQE7vPodVkZ9HEagphJ0QWgs7qgED+CWdUJ7YlgiE130UNm9OLPOb"
            + "mCJzDWWBZA1+q7396Li3zx6Et/fdB+ikDpH1dhHmWFDIBmQeevKMqlNeTUOcKLbZlfRCmClhwwYXBGCh0CgYEAzTEMg4KQUGAmQGoseBy"
            + "5nIAHRzAP/kLo139RcWZmzeIlbBnKzjertyr+wcCn8gydteOXIOcrkn4T+WfTW/Q0Xj/+zUIUmumnCGMyD8fxdVPYjSvlbmO+za2OrGY2"
            + "3BXpQECOsgJSe1Xy/4zznlEphSUrVjso6JvrJQBlHff973cCgYEAktZsANieIwdXVqujlrF4tJcqGexYm1Yi69Q2Svqd61MRQ6Toh2ZsK"
            + "tnC9HIUvVUgFmAiM/NRkclUmu7mm7DTpPSrBpod1tlmY6bx8wJsqSQV6Dvmy76L+tCd4HZAvqFbPxCvKRTTSuhkMW4NCyjB7aAfpKY3mh"
            + "D3eImoziApfbUCgYBpeIhbQuGMUM5JaGpl9lefcKjiV/EQIQOgIK/vdnzKsjJp1OaDbkRwEaKPZuotUQ7jGcencsBQ6JEA08zjSeZ2LP2"
            + "NQMvdmAkVobet6M3UJh+gfv9WhrxZM5auRLUxtaxkJJmve9Huvii9mqieAM3QFJMIzBa2RkvrTasNNP9e0Q==";

    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzxiHJgoK2CFZsk3IBBm7xR0z1CRqwS"
            + "T0XoO+p8dd3uLFiFZZ+IwD86mR+ZPkkzPitQ4C9qp+fouyL1k1mT3Uab8BGSuJNYWVGQdwsQ1I7Wf8IF+PNf+y5pwWjTPXIiLVbm/1Eu5"
            + "Uh13JM+gJfxaNwcZQfVwhmahTkQZktM6zfi+DDZiNbldaDmEOO1nsLzu5kcn37oXX4zRGng1jtJ3pRASPm96ddm7vbHL9MGpN+JVC0EcX"
            + "PLwyoNeeDt+W4fxFmqxFjL6VvHE46Qc35WqfCm+e1lQ7/9Zf2tPZ5fZG4MmsscMt6APAJ3zaRJjAUkdQy9CvxVco+PuJWwUBFoy56wIDAQAB";

    private static final String ISSUER = "athena";

    private static final String AUDIENCE = "web";

    static {
        try {
            rsaPrivateKey = (RSAPrivateKey) RSAUtil.getPrivateKey(PRIVATE_KEY);
            rsaPublicKey = (RSAPublicKey) RSAUtil.getPublicKey(PUBLIC_KEY);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("获取 JWT 公钥 私钥 异常: {}", e.getMessage());
        }
    }

    private JWTUtil() {
    }

    /**
     * 创建 jwt token
     *
     * @param subject 令牌主题
     * @param minutes 有效分钟数
     */
    public static String createToken(String subject, long minutes) throws JoseException {
        JwtClaims claims = new JwtClaims();
        claims.setIssuer(ISSUER);  // 令牌的创建者
        claims.setAudience(AUDIENCE); // 要将令牌发送给谁
        claims.setExpirationTimeMinutesInTheFuture(minutes); // 令牌到期的时间 从现在起 分钟数
        claims.setGeneratedJwtId(); // 用于令牌的唯一标识符
        claims.setIssuedAtToNow();  // 发出/创建令牌时（现在）
        claims.setNotBeforeMinutesInThePast(2); // 令牌尚未生效的时间（2分钟前）
        claims.setSubject(subject); // 主题/委托人是令牌的对象

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(rsaPrivateKey);
        jws.setKeyIdHeaderValue(PRIVATE_KEY_ID);
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA512);
        return jws.getCompactSerialization();
    }

    /**
     * 验证 jwt token 的有效性
     */
    public static JwtClaims validation(String jwtToken, int minutes) throws InvalidJwtException {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // JWT必须有一个到期时间
                .setMaxFutureValidityInMinutes(minutes) // 但到期时间不能太疯狂
                .setAllowedClockSkewInSeconds(30) // 允许一些余地来验证基于时间的索赔，以解决时钟偏差问题
                .setRequireSubject() // 必须有一个主题声明
                .setExpectedIssuer(ISSUER) // 需要由谁发出
                .setExpectedAudience(AUDIENCE) // 目标对象
                .setVerificationKey(rsaPublicKey) // 使用公钥验证签名
                .build();

        //  验证JWT并将其处理为声明
        JwtClaims jwtClaims = jwtConsumer.processToClaims(jwtToken);
        logger.debug("JWT validation succeeded! {}", jwtClaims);
        return jwtClaims;
    }

}
