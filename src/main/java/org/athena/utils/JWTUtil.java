package org.athena.utils;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * jwt 工具类
 */
public final class JWTUtil {

    private static Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    /**
     * 生成一个RSA密钥对，用于JWT的签名和验证
     */
    private static RsaJsonWebKey rsaJsonWebKey;

    private static final String ISSUER = "athena";

    private static final String AUDIENCE = "web";

    static {
        try {
            rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
            rsaJsonWebKey.setKeyId("岁月无声");
        } catch (JoseException e) {
            logger.error(e.getMessage());
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
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA512);
        return jws.getCompactSerialization();
    }

    /**
     * 验证 jwt token 的有效性
     */
    public static JwtClaims validation(String jwtToken) throws InvalidJwtException {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // JWT必须有一个到期时间
                .setMaxFutureValidityInMinutes(300) // 但到期时间不能太疯狂
                .setAllowedClockSkewInSeconds(30) // 允许一些余地来验证基于时间的索赔，以解决时钟偏差问题
                .setRequireSubject() // 必须有一个主题声明
                .setExpectedIssuer("Issuer") // 需要由谁发出
                .setExpectedAudience("Audience") // 目标对象
                .setVerificationKey(rsaJsonWebKey.getKey()) // 使用公钥验证签名
                .build();

        //  验证JWT并将其处理为声明
        JwtClaims jwtClaims = jwtConsumer.processToClaims(jwtToken);
        logger.debug("JWT validation succeeded! {}", jwtClaims);
        return jwtClaims;
    }

}
