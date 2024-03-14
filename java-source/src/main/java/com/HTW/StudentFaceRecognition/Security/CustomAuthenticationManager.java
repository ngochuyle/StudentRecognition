package com.HTW.StudentFaceRecognition.Security;

import com.HTW.StudentFaceRecognition.Security.CustomUserDetails.PersonnelUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    PersonnelUserDetails personnelUserDetails;
    UsernamePasswordAuthenticationToken authToken = null;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String uname = String.valueOf(authentication.getName());
        String upassw = String.valueOf(authentication.getCredentials());
        if (authentication == null) {
            return null;
        }
        UserDetails userDetails = personnelUserDetails.loadUserByUsername(uname);
        log.info("userDetail :"+userDetails.toString());
        if(userDetails==null){
            log.info(new UsernameNotFoundException(String.format("Username not found").toString()));
            return null;
        }
        if(userDetails.getPassword().equals(upassw)){
            authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            log.info("authToken :"+authToken);
        }else{

            log.info("authToken false:");
            return null;
        }
        return authToken;
    }
}
