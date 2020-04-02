package com.yao.app.http;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.yao.app.java.date.DateExtUtils;
import com.yao.app.protocol.thrift.TestZip;
import com.yao.app.protocol.thrift.service.TUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtStudy {

    /**
     * iss: 该JWT的签发者
     * sub: 该JWT所面向的用户
     * aud: 接收该JWT的一方
     * exp(expires): 什么时候过期，这里是一个Unix时间戳
     * iat(issued at): 在什么时候签发的
     *
     *
     *
     *
     * @param args
     */
    public static void main(String[] args) {

    }

    public static void test1() throws Exception {
        // jjwt
        String secretStr = "a/qIqFQO+nFdgFOrVz3lr4myAY9GOqyKTzdOyKCKmWw=";
        SecretKey key2 = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String str2 = Base64.getEncoder().encodeToString(key2.getEncoded());
        System.out.println(str2);

        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(10);

        TUserDetail userDetail = TestZip.createOne();

        Key key = Keys.hmacShaKeyFor(secretStr.getBytes(StandardCharsets.UTF_8));
        String jws = Jwts.builder().claim("user", userDetail).setExpiration(DateExtUtils.asDate(expireTime)).signWith(key).compact();

        System.out.println(jws);
        System.out.println(jws.toCharArray().length);
        System.out.println(jws.length()); //355

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);
            System.out.println(claimsJws);
        } catch (JwtException e) {

            //don't trust the JWT!
        }
    }

    public static void test21() throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] sharedSecret = new byte[32];
        random.nextBytes(sharedSecret);
        // Create HMAC signer
        JWSSigner signer = new MACSigner(sharedSecret);

        // Prepare JWS object with "Hello, world!" payload
        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload("Hello, world!"));

        // Apply the HMAC
        jwsObject.sign(signer);

        // To serialize to compact form, produces something like
        // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
        String s = jwsObject.serialize();

        // To parse the JWS and verify it, e.g. on client-side
        jwsObject = JWSObject.parse(s);

        JWSVerifier verifier = new MACVerifier(sharedSecret);
        jwsObject.verify(verifier);
    }

    public static void test22() throws Exception {
        // nimbus-jose-jwt
        // Generate random 256-bit (32-byte) shared secret
        SecureRandom random = new SecureRandom();
        byte[] sharedSecret = new byte[32];
        random.nextBytes(sharedSecret);

        // Create HMAC signer
        JWSSigner signer = new MACSigner(sharedSecret);

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .subject("alice")
            .issuer("https://c2id.com")
            .expirationTime(new Date(new Date().getTime() + 60 * 1000))
            .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        // Apply the HMAC protection
        signedJWT.sign(signer);

        // Serialize to compact form, produces something like
        // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
        String s = signedJWT.serialize();

        // On the consumer side, parse the JWS and verify its HMAC
        signedJWT = SignedJWT.parse(s);

        JWSVerifier verifier = new MACVerifier(sharedSecret);
        signedJWT.verify(verifier);

        //assertTrue(signedJWT.verify(verifier));

        // Retrieve / verify the JWT claims according to the app requirements
        // assertEquals("alice", signedJWT.getJWTClaimsSet().getSubject());
        // assertEquals("https://c2id.com", signedJWT.getJWTClaimsSet().getIssuer());
        // assertTrue(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()));
    }
}
