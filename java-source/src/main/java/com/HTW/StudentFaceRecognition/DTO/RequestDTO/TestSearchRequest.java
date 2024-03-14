package com.HTW.StudentFaceRecognition.DTO.RequestDTO;

import lombok.*;

import java.util.List;
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestSearchRequest {

    private String name;
    private String professorName;
    private List<Long> courseIds;
}
