package com.HTW.StudentFaceRecognition.Converter;

import com.HTW.StudentFaceRecognition.DTO.RequestDTO.StudentRequest;
import com.HTW.StudentFaceRecognition.Model.Entity.StudentEntity;

public class StudentRequestToEntity {
    public static StudentEntity convertToEntity(StudentRequest studentRequest) {
        return StudentEntity.builder()
                .name(studentRequest.getName())
                .sNummer(studentRequest.getSNummer())
                .build();
    }
}
