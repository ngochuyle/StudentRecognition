package com.HTW.StudentFaceRecognition.DTO.RequestDTO;

import lombok.*;

import java.util.List;
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestCreateRequestDTO {
    private Long id;
    private String name;
    private String description;
    private Long courseId;
}
