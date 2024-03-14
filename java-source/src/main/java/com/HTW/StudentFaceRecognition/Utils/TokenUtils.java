package com.HTW.StudentFaceRecognition.Utils;

import com.HTW.StudentFaceRecognition.Constant.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
@Log4j2
public class TokenUtils {
    private static Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SecurityConstant.JWT_SECRET),
            SignatureAlgorithm.HS512.getJcaName());
    public static String generateJWTUserToken(String userName) {
        //String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstant.EXPIRATION_TIME);


        String token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                //.signWith(null, null)
                .signWith(hmacKey)
                //.signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                .compact();
        return token;
    }
    public static String getUsernameFromJWTUserToken(String token) {
         Claims claims = Jwts.parser()
                 .setSigningKey(SecurityConstant.JWT_SECRET)
                 .parseClaimsJws(token)
                 .getBody();

        return claims.getSubject();
    }
    public static boolean isJWTTokenValid(String token) {
        try {
            //Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);
            Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
    public static String[] decodedBase64(String token) {

        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String pairedCredentials = new String(decodedBytes);
        String[] credentials = pairedCredentials.split(":", 2);

        return credentials;

    }
}
