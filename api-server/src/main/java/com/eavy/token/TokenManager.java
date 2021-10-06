package com.eavy.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenManager {

    @Value("${JWT_HMAC_KEY}")
    public void init(String key) {
        TokenManager.key = key;
        algorithm = Algorithm.HMAC256(key.getBytes(StandardCharsets.UTF_8));
        verifier = JWT.require(algorithm).build();
    }

    private static String key;
    private static Algorithm algorithm;
    private static JWTVerifier verifier;

    public static String generateAccessToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static String generateRefreshToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) {
        return verifier.verify(token);
    }

}
