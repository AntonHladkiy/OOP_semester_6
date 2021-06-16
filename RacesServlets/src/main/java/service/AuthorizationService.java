package service;

import entity.User;
import entity.response.TokenResponse;
import util.ApplicationProperties;
import util.Encryptor;
import io.jsonwebtoken.*;

import java.util.Date;

public class AuthorizationService {
    public static AuthorizationService INSTANCE = new AuthorizationService();

    private static final UserService userService = UserService.INSTANCE;

    private static final String signature = ApplicationProperties.get("signature");
    private static final Long ttlMillis = Long.valueOf(ApplicationProperties.get("ttlMillis"));

    private AuthorizationService() {}

    public TokenResponse authorize(String username, String password) {
        User user = userService.getByUsername(username);

        if (!user.getPassword().equals(Encryptor.encode(password))) {
            throw new RuntimeException("Wrong credentials");
        }

        return new TokenResponse(
                createJWT(user), user.getRole(),user.getUsername());
    }

    public static String createJWT(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder =
                Jwts.builder()
                        .setIssuedAt(now)
                        .setSubject(user.getUsername())
                        .claim("role", user.getRole())
                        .claim("user_id", user.getId())
                        .setExpiration(new Date(nowMillis + ttlMillis))
                        .signWith(SignatureAlgorithm.HS256, signature);

        return builder.compact();
    }

    public static Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
    }

    public static String getRole(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        String authoritiesStr = claimsJws.getBody().get("role").toString();
        return authoritiesStr;
    }

    public static Integer getUserId(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        return Integer.parseInt(claimsJws.getBody().get("user_id").toString());
    }
}