package com.HTW.StudentFaceRecognition.DTO.RequestDTO;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCourseRequest {
    private Long studentID;
    private List<Long> selectedCourseIds;
}
