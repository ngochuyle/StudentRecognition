package com.HTW.StudentFaceRecognition.Utils;

import com.HTW.StudentFaceRecognition.Constant.SecurityConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
@Log4j2
public class SecurityUtils {
    public static UsernamePasswordAuthenticationToken getUnauthenticatedToken(String headerToken) {
        // Initialize an unauthenticated token with empty information
        UsernamePasswordAuthenticationToken unAuthenticatedToken = new UsernamePasswordAuthenticationToken("", "");

        // Check if the header exists and is in the correct format
        if (!isAuthorizationHeaderValid(headerToken, SecurityConstant.BASIC_TOKEN_PREFIX)) {
            log.info("Authorization header is missing, empty, or does not start with 'Basic'");
            return unAuthenticatedToken;
        }

        // Remove the 'Basic ' prefix and decode the value from Base64
        String base64Credentials = headerToken.substring(SecurityConstant.BASIC_TOKEN_PREFIX.length()).trim();
        String decodedCredentials;
        try {
            decodedCredentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            log.info("Error decoding credentials: " + e.getMessage());
            return unAuthenticatedToken;
        }

        // Split the string to get the username and password
        String[] credentials = decodedCredentials.split(":", 2);
        if (credentials.length != 2 || !StringUtils.hasText(credentials[0]) || !StringUtils.hasText(credentials[1])) {
            log.info("Invalid format of decoded credentials. Expected format is 'username:password'.");
            return unAuthenticatedToken;
        }

        // Returns a token with information from the header
        return new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]);
    }

    private static boolean isAuthorizationHeaderValid(String headerToken, String prefix) {
        return headerToken != null && headerToken.trim().startsWith(prefix);
    }
}
