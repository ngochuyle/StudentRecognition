package com.HTW.StudentFaceRecognition.Service;

import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.StudentReponse;
import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.TestReponse;
import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.TestReponseDTO;
import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.TestStudentReponse;
import com.HTW.StudentFaceRecognition.DTO.RequestDTO.StudentRequest;
import com.HTW.StudentFaceRecognition.DTO.RequestDTO.TestCreateRequestDTO;
import com.HTW.StudentFaceRecognition.DTO.RequestDTO.TestSearchRequest;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface ITestService {
     List<TestReponseDTO> findByCustomTest(TestSearchRequest testSearchRequest);
     Boolean createTest(TestCreateRequestDTO testCreateRequestDTO);
     Boolean updateTest(TestCreateRequestDTO updateTestDTO);
     Boolean deleteTest(Long testID);
     List<TestStudentReponse> getAllStudentByTestID(Long testId);
     Boolean updateScoreByStudentID(Long studentID, BigDecimal score, Long testRegistrationId);
     Boolean updateScoreByTestRegistrationId(Long testRegistrationId);

     String searchIMG(MultipartFile imageFile);
}