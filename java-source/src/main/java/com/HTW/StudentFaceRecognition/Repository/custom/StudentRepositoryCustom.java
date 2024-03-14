package com.HTW.StudentFaceRecognition.Repository.custom;

import com.HTW.StudentFaceRecognition.DTO.RequestDTO.StudentRequest;
import com.HTW.StudentFaceRecognition.Model.Entity.StudentEntity;

import java.util.List;

public interface StudentRepositoryCustom {
    List<StudentEntity> searchStudent(StudentRequest studentRequest);
}
