package com.HTW.StudentFaceRecognition.Service;

import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.StudentReponse;
import com.HTW.StudentFaceRecognition.DTO.RequestDTO.StudentRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IStudentService {
    StudentReponse findStudentByID(Long id);
    Boolean createStudent(StudentRequest studentRequest);

    List<StudentReponse> searchStudent(StudentRequest studentRequest);
    List<StudentReponse> findByCustomStudent(StudentRequest studentRequest);

    Boolean deleteStudentByID(Long studentId);
    Boolean updateStudent(StudentRequest studentRequest);
}
