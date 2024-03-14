package com.HTW.StudentFaceRecognition.Converter;

import com.HTW.StudentFaceRecognition.DTO.RequestDTO.StudentRequest;
import com.HTW.StudentFaceRecognition.Model.Entity.StudentEntity;

public class StudentRequestToEntity {
    public static StudentEntity convertToEntity(StudentRequest studentRequest) {
        // Tạo và trả về một thực thể Student từ StudentRequest
        // Đảm bảo rằng tất cả trường thông tin cần thiết đã được thiết lập
        return StudentEntity.builder()
                .name(studentRequest.getName())
                .sNummer(studentRequest.getSNummer())
                // Set các trường khác nếu cần
                .build();
    }
}
