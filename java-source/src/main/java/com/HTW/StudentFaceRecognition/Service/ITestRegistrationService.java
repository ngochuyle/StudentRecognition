package com.HTW.StudentFaceRecognition.Service;

import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.TestReponse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITestRegistrationService {
    List<TestReponse> findTestByStudentID(Long studentId);

}
