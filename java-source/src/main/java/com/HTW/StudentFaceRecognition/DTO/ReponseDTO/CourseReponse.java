package com.HTW.StudentFaceRecognition.DTO.ReponseDTO;

import jakarta.persistence.Column;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CourseReponse {
    private Long courseId;
    private String name;
    private String description;
}
