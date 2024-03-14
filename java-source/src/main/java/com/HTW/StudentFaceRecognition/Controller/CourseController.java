package com.HTW.StudentFaceRecognition.Controller;

import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.CourseReponse;
import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.StudentReponse;
import com.HTW.StudentFaceRecognition.Service.impl.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@Log4j2
public class CourseController {
    @Autowired
    CourseService courseService;
    @GetMapping("/course/{studentId}")
    public ResponseEntity<Set<CourseReponse>> getCourseByStudentID(@PathVariable Long studentId) {
        log.info("Student ID search course:"+studentId);
        Set<CourseReponse> result = courseService.getCourseByStudentID(studentId);
        log.info("result"+result.toString());
        return ResponseEntity.ok(result);
    }
    @GetMapping("/course/")
    public ResponseEntity<List<CourseReponse>> getAllCourse() {
        List<CourseReponse> result = courseService.getAllCourse();
        log.info("result"+result);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/course/sign/")
    public ResponseEntity<List<CourseReponse>> signCourseStudent() {
        List<CourseReponse> result = courseService.getAllCourse();
        log.info("result"+result);
        return ResponseEntity.ok(result);
    }

}
