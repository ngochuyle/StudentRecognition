package com.HTW.StudentFaceRecognition.DTO.ReponseDTO;

import lombok.*;
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestReponseDTO {
        private Long id;
        private String title;
        private String description;
        private String coursename;
        private String professor;

}
