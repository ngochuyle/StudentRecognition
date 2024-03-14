package com.HTW.StudentFaceRecognition.Service;

import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.CourseReponse;

import java.util.List;
import java.util.Set;

public interface ICourseService {
    Set<CourseReponse> getCourseByStudentID(Long studentId);
    List<CourseReponse> getAllCourse();
}
