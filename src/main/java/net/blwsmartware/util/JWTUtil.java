package net.blwsmartware.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import net.blwsmartware.model.UserModel;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;

public class JWTUtil {
    public static  final String SECRET= ResourceBundle.getBundle("secret").getString("SECRET");

    public  static  final long EXPIRATION_TIME = 2*60*1000;
    public  static String generateToken(UserModel userModel){
        Algorithm algorithm = Algorithm.HMAC384(SECRET);
        Builder buildToken = JWT.create()
                .withClaim("id",userModel.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID().toString());
        return buildToken.sign(algorithm);
    }
    public static DecodedJWT verifyToken(String token){
        Algorithm algorithm = Algorithm.HMAC384(SECRET);
        JWTVerifier verifier =JWT.require(algorithm).build();
        return verifier.verify(token);
    }
    public static Long getIdUserFromToken(String token){
        DecodedJWT jwt =verifyToken(token);
        return jwt.getClaim("id").asLong();
    }
}
