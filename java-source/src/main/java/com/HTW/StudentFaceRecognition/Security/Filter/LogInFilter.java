package com.HTW.StudentFaceRecognition.Security.Filter;

import com.HTW.StudentFaceRecognition.Constant.SecurityConstant;
import com.HTW.StudentFaceRecognition.Security.CustomAuthenticationManager;
import com.HTW.StudentFaceRecognition.Utils.SecurityUtils;
import com.HTW.StudentFaceRecognition.Utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@Component
public class LogInFilter extends OncePerRequestFilter {
    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("daa+   " + request.getRequestURI());
        log.info("Request: "+request.getHeader("AuthorizationBasic"));


        try {

            log.info("Token: " + request.getHeader(SecurityConstant.AUTH_HEADER_BASIC));
            UsernamePasswordAuthenticationToken authentication = SecurityUtils.getUnauthenticatedToken(request.getHeader(SecurityConstant.AUTH_HEADER_BASIC));
            Authentication authResult = customAuthenticationManager.authenticate(authentication);
            if(authResult==null){
                log.info("Failed to process authentication request: user or password false");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            log.info("authRéult" + authResult);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            String token = TokenUtils.generateJWTUserToken(authResult.getName());
            log.info("Token return: " + token);
            //response.set
            response.setHeader("Token", token);
        } catch (Exception ex) {
            log.info("Failed to process authentication request: " + ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String pathToApply = "/signin";
        String path = request.getRequestURI();
        return !path.equals(pathToApply);
    }

}
