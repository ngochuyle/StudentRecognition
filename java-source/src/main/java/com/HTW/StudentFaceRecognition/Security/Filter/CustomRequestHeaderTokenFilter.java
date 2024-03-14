package com.HTW.StudentFaceRecognition.Security.Filter;

import com.HTW.StudentFaceRecognition.Constant.SecurityConstant;
import com.HTW.StudentFaceRecognition.Security.CustomUserDetails.PersonnelUserDetails;
import com.HTW.StudentFaceRecognition.Utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@Component
public class CustomRequestHeaderTokenFilter extends OncePerRequestFilter {
    @Autowired
    private PersonnelUserDetails personnelUserDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader(SecurityConstant.AUTH_HEADER_TOKEN);
            if (token != null && !token.isEmpty() && TokenUtils.isJWTTokenValid(token)) {
                String uname = TokenUtils.getUsernameFromJWTUserToken(token);
                UserDetails userDetails = personnelUserDetails.loadUserByUsername(uname);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(uname, null, userDetails.getAuthorities());
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);
                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                    filterChain.doFilter(request, response);
                } else {
                    log.info("user not found");
                    setUnsuccessfulResponse(response, "user not found");
                    return;
                }
            } else {
                log.info("token is null or empty or not valid");
                setUnsuccessfulResponse(response, "token is null or empty or not valid");
                return;
            }
        } catch (Exception ex) {
            log.error("Failed to process authentication request: " + ex.getMessage());
            setUnsuccessfulResponse(response, "Failed to process authentication request:");
            return;
        }
    }

    private void setUnsuccessfulResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"" + message + "\"}");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Định nghĩa đường dẫn mà bạn không muốn filter này áp dụng
        String pathToExclude = "/signin";  // Thay đổi thành đường dẫn cụ thể bạn muốn loại trừ

        // Lấy URI hiện tại từ request
        String path = request.getRequestURI();

        // Kiểm tra xem path hiện tại có phải là path cần loại trừ không
        return path.equals(pathToExclude)|| path.startsWith("/images/");
    }
}
