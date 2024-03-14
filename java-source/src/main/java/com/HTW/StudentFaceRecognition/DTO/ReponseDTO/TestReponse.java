package com.HTW.StudentFaceRecognition.DTO.ReponseDTO;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestReponse {
    private Long id;
    private String title;
    private String description;
    private String coursename;
    private String professor;
    private BigDecimal score;
    private String participated;
}
