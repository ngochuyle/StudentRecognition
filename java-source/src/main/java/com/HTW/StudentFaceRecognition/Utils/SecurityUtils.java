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
        // Khởi tạo một token không xác thực với thông tin rỗng
        UsernamePasswordAuthenticationToken unAuthenticatedToken = new UsernamePasswordAuthenticationToken("", "");

        // Kiểm tra xem header có tồn tại và đúng định dạng không
        if (!isAuthorizationHeaderValid(headerToken, SecurityConstant.BASIC_TOKEN_PREFIX)) {
            log.info("Authorization header is missing, empty, or does not start with 'Basic'");
            return unAuthenticatedToken;
        }

        // Loại bỏ tiền tố 'Basic ' và giải mã giá trị từ Base64
        String base64Credentials = headerToken.substring(SecurityConstant.BASIC_TOKEN_PREFIX.length()).trim();
        String decodedCredentials;
        try {
            decodedCredentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            log.info("Error decoding credentials: " + e.getMessage());
            return unAuthenticatedToken;
        }

        // Tách chuỗi để lấy username và password
        String[] credentials = decodedCredentials.split(":", 2);
        if (credentials.length != 2 || !StringUtils.hasText(credentials[0]) || !StringUtils.hasText(credentials[1])) {
            log.info("Invalid format of decoded credentials. Expected format is 'username:password'.");
            return unAuthenticatedToken;
        }

        // Trả về một token có thông tin từ header
        return new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]);
    }

    private static boolean isAuthorizationHeaderValid(String headerToken, String prefix) {
        return headerToken != null && headerToken.trim().startsWith(prefix);
    }
}
