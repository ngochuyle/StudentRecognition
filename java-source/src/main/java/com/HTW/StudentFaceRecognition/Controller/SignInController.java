package com.HTW.StudentFaceRecognition.Controller;

import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.SingInReponse;
import com.HTW.StudentFaceRecognition.Service.impl.PersonnelService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@Log4j2
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class SignInController {
    @Autowired
    PersonnelService personnelService;
    @GetMapping("/signin")
    public void singIn(HttpServletRequest httpServletRequest) {
    }
    private static String extractUsername(String encoded) {
        if (encoded.startsWith("Basic ")) {
            encoded = encoded.substring(6);
        }

        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        String decodedStr = new String(decodedBytes);

        String[] parts = decodedStr.split(":");
        return parts.length > 0 ? parts[0] : null;
    }
}
