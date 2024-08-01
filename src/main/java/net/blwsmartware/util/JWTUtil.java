package net.blwsmartware.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.model.UserModel;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;

public class JWTUtil {
    public static final String SECRET = ResourceBundle.getBundle("secret").getString("SECRET");

    public static final long EXPIRATION_TIME = 2 * 60 * 60 * 1000;

    public static String generateToken(UserModel userModel) {
        Algorithm algorithm = Algorithm.HMAC384(SECRET);
        Builder buildToken = JWT.create()
                .withClaim("id", userModel.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID().toString());
        return buildToken.sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC384(SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public static Long getIdUserFromToken(String token) {
        DecodedJWT jwt = verifyToken(token);
        return jwt.getClaim("id").asLong();
    }

    public static Cookie getCookieToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token"))
                    return cookie;
            }
        }
        return null;
    }

    public static String getToken(HttpServletRequest request) {
        Cookie cookie = getCookieToken(request);
        if (cookie != null && !cookie.getValue().isEmpty())
            return cookie.getValue();
        return null;
    }

    public static void destroyToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
}
