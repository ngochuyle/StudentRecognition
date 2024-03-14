package com.HTW.StudentFaceRecognition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class StudentFaceRecognitionApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentFaceRecognitionApplication.class, args);
	}

}
