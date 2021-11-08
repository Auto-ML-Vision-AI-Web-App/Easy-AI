package com.eavy.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

@Component
public class TokenManager {

    private static final HashMap<String, String> usernameToRefreshToken = new HashMap<>();

    @Value("${JWT_HMAC_KEY}")
    public void init(String key) {
        TokenManager.key = key;
        algorithm = Algorithm.HMAC256(key.getBytes(StandardCharsets.UTF_8));
        verifier = JWT.require(algorithm).build();
    }

    private static String key;
    private static Algorithm algorithm;
    private static JWTVerifier verifier;

    public static String generateAccessToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .sign(algorithm);
    }

    public static String generateAccessToken(User user) {
        return generateAccessToken(user.getUsername());
    }

    public static String generateRefreshToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 6 * 60 * 60 * 1000))
                .sign(algorithm);
    }

    public static String generateRefreshToken(User user) {
        return generateRefreshToken(user.getUsername());
    }

    public static DecodedJWT verifyToken(String token) {
        return verifier.verify(token);
    }

    public static String save(String username, String refreshToken) {
        return usernameToRefreshToken.put(username, refreshToken);
    }

    public static boolean contains(String refreshToken) {
        return usernameToRefreshToken.containsValue(refreshToken);
    }

    public static String remove(String username) {
        return usernameToRefreshToken.remove(username);
    }

}
