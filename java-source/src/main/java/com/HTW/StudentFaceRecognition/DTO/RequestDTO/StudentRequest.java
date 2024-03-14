package com.HTW.StudentFaceRecognition.DTO.RequestDTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private Long id;
    private String name;
    private String sNummer;
    private List<MultipartFile> images;
    private List<Long> CourseIDs;
    private List<Long> TestIDs;
}
