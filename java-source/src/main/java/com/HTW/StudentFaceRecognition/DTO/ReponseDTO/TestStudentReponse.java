package com.HTW.StudentFaceRecognition.DTO.ReponseDTO;

import com.HTW.StudentFaceRecognition.Model.Entity.CourseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestStudentReponse {
    private Long id;
    private String name;
    private String sNummer;
    private List<String> ImgURL;
    private BigDecimal score;
    private String participated;
    private int testRegistrationId;
}
