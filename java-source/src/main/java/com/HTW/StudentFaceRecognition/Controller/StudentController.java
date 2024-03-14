package com.HTW.StudentFaceRecognition.Controller;

import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.StudentReponse;
import com.HTW.StudentFaceRecognition.DTO.RequestDTO.RegisterCourseRequest;
import com.HTW.StudentFaceRecognition.DTO.RequestDTO.StudentRequest;
import com.HTW.StudentFaceRecognition.Repository.StudentRepository;
import com.HTW.StudentFaceRecognition.Service.impl.StudentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Log4j2
public class StudentController {

    @Autowired
    StudentRepository studentReposioty;
    @Autowired
    StudentService studentService;

    /*@GetMapping("/student/{id}")
    public StudentEntity getStudentByID(@PathVariable Long id) {
        Optional<StudentEntity> result = studentReposioty.findById(id);
        StudentEntity student = null;
        if (result.isPresent()) {
            student = result.get();
            log.info("Student found: " + student);
        } else {
            log.info("No student found with ID: " + id);
        }
        return student;
    }*/

    @GetMapping("/images/")
    public String getIMGByID() {
        String basePath = "http://localhost:8080/student/img/";
        String imageName = "79547_orginal_1.jpg";
        log.info(basePath + imageName);
        // Tạo và trả về đường dẫn đầy đủ
        return basePath + imageName;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<StudentReponse> getStudentImages(@PathVariable Long studentId) {
        log.info("studentID: " + studentId);
        StudentReponse studentReponse = studentService.findStudentByID(studentId);
        log.info("test:" + studentReponse);
        return ResponseEntity.ok(studentReponse);
    }

    @PostMapping("/student/create")
    public ResponseEntity<?> createStudent(@ModelAttribute StudentRequest studentRequest) {
        log.info("Request IMG:  "+studentRequest.getImages());
        log.info("Request"+studentRequest);
        if(studentService.createStudent(studentRequest)){
            return ResponseEntity.ok("Successfully uploaded!");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload!");
        }
    }
    //    private List<Long> TestIDs;
    @PostMapping("/student/update")
    public ResponseEntity<?> updateStudentByStudentID(@ModelAttribute StudentRequest studentRequest) {
        log.info("Request"+studentRequest);
        if(studentService.updateStudent(studentRequest)) return ResponseEntity.ok("sucess");
        else  return  ResponseEntity.badRequest().body("unsucess");
    }
    @PostMapping("/student/updateIMG")
    public ResponseEntity<?> updateStudentIMGByStudentID(@ModelAttribute StudentRequest studentRequest) throws IOException, InterruptedException {
        log.info("Request "+studentRequest.getId());
        log.info("Request IMG:  "+studentRequest.getImages());
        if(studentService.updateIMG(studentRequest)) return ResponseEntity.ok("sucess");
        return ResponseEntity.badRequest().body("unsucess");
    }
    //    private List<Long> TestIDs;
    @PostMapping("/student/registerCourse")
    public ResponseEntity<?> registerCourse(@RequestBody RegisterCourseRequest request) {

        log.info("Request: "+request.toString());
        studentService.updateStudentCourses(request.getStudentID(),request.getSelectedCourseIds());
        return ResponseEntity.ok("sucess");
    }
    @PostMapping("/student/delete/{studentId}")
    public ResponseEntity<?> deleteStudentByStudentID(@PathVariable Long studentId) {
        Boolean result = studentService.deleteStudentByID(studentId);
        if(result==true) return  ResponseEntity.ok("Der Student/in mit der ID: " + studentId + " wurde erfolgreich gelöscht.");
        else return ResponseEntity.badRequest().body("Der Student/in mit der ID: " + studentId + " konnte nicht gelöscht werden.");
    }
    @GetMapping("student/search")
    public ResponseEntity<List<StudentReponse>> searchStudent(        @RequestParam(required = false) String name,
                                                                @RequestParam(required = false) String sNummer,
                                                                @RequestParam(required = false) List<Long> CourseIDs){
        StudentRequest studentRequest = StudentRequest.builder()
                .name(name)
                .sNummer(sNummer)
                .CourseIDs(CourseIDs)
                .build();
        log.info("Param: "+studentRequest);
        return ResponseEntity.ok(studentService.searchStudent(studentRequest));
    }

}
