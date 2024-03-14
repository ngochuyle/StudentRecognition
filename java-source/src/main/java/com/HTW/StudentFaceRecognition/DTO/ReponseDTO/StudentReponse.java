package com.HTW.StudentFaceRecognition.DTO.ReponseDTO;

import com.HTW.StudentFaceRecognition.Model.Entity.CourseEntity;
import lombok.*;

import java.util.List;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentReponse {
    private Long id;
    private String name;
    private String sNummer;
    private List<String> ImgURL;
    private List<CourseEntity> courses;
}
