package com.HTW.StudentFaceRecognition.Security.CustomUserDetails;

import com.HTW.StudentFaceRecognition.Model.Personnel;
import com.HTW.StudentFaceRecognition.Repository.PersonnelRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Log4j2
@Service
public class PersonnelUserDetails implements UserDetailsService {
    @Autowired
    private PersonnelRepository personnelRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName, password;
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Personnel> personnels = personnelRepository.findByName(username);
        if (personnels.size() == 0) {
            throw new UsernameNotFoundException("User details not found for the user : " + username);
        } else{
            userName = personnels.get(0).getName();
            password = personnels.get(0).getPwd();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(personnels.get(0).getRole()));
        }
        return new User(userName,password,authorities);
    }
}
