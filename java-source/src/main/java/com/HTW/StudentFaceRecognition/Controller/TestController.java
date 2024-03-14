package com.HTW.StudentFaceRecognition.Controller;

import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.TestReponse;
import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.TestReponseDTO;
import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.TestStudentReponse;
import com.HTW.StudentFaceRecognition.DTO.RequestDTO.TestCreateRequestDTO;
import com.HTW.StudentFaceRecognition.DTO.RequestDTO.TestSearchRequest;
import com.HTW.StudentFaceRecognition.Service.ITestRegistrationService;
import com.HTW.StudentFaceRecognition.Service.ITestService;
import com.HTW.StudentFaceRecognition.Utils.FileUItils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Log4j2
public class TestController {
    @Autowired
    ITestRegistrationService testRegistrationService;
    @Autowired
    ITestService testService;

    @GetMapping("/test/{studentId}")
    public ResponseEntity<List<TestReponse>> getTestByStudentID(@PathVariable Long studentId) {
        log.info("studentID: " + studentId);
        List<TestReponse> testReponses = testRegistrationService.findTestByStudentID(studentId);
        log.info("studentID: sucess     " + studentId);
        return ResponseEntity.ok(testReponses);
    }

    @GetMapping("test/search")
    public ResponseEntity<List<TestReponseDTO>> searchStudent(@RequestParam(required = false) String name,
                                                              @RequestParam(required = false) String professorName,
                                                              @RequestParam(required = false) List<Long> courseIds) {
        TestSearchRequest testSearchRequest = TestSearchRequest.builder()
                .name(name)
                .professorName(professorName)
                .courseIds(courseIds)
                .build();
        log.info("Param: " + testSearchRequest);
        return ResponseEntity.ok(testService.findByCustomTest(testSearchRequest));
    }

    @PostMapping("/test/create")
    public ResponseEntity<?> createTest(@ModelAttribute TestCreateRequestDTO testCreateRequestDTO) {
        log.info("Request" + testCreateRequestDTO);
        if (testService.createTest(testCreateRequestDTO)) {
            return ResponseEntity.ok("Successfully uploaded!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload!");
        }
    }

    @PostMapping("/test/update")
    public ResponseEntity<?> updateTest(@ModelAttribute TestCreateRequestDTO updateTestDTO) {
        log.info("Request1" + updateTestDTO);
        if (testService.updateTest(updateTestDTO)) {
            return ResponseEntity.ok("Sucessfully update");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update");
        }
    }

    @PostMapping("/test/delete/{testId}")
    public ResponseEntity<?> deleteStudentByStudentID(@PathVariable Long testId) {
        log.info("delate Test wwith ID: " + testId);
        if (testService.deleteTest(testId)) {
            log.info("Sucessfully delete test with ID" + testId);
            return ResponseEntity.ok("Sucessfully delete test with ID" + testId);
        } else {
            log.info("Failed to update test with ID: " + testId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update test with ID: " + testId);
        }

    }

    @GetMapping("/testPage/{testId}")
    public ResponseEntity<?> getAllStudentByTestID(@PathVariable Long testId) {
        log.info("get all student by Test with ID: " + testId);
        List<TestStudentReponse> testStudentReponses = testService.getAllStudentByTestID(testId);
        if (testStudentReponses.size() > 0) {
            log.info("Sucessfully get all student   " + testStudentReponses.size());
            return ResponseEntity.ok(testStudentReponses);
        } else {
            log.info("Failed to get all student with testID: " + testId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get all student with testID: " + testId);
        }
    }

    @GetMapping("test/updateScore")
    public ResponseEntity<String> updateScore(@RequestParam(required = false) BigDecimal score,
                                              @RequestParam(required = false) Long id,
                                              @RequestParam(required = false) Long testRegistrationId) {
        log.info("Param: " + score + ", " + id + ", " + testRegistrationId);
        if (testService.updateScoreByStudentID(id, score, testRegistrationId)) {
            log.info("thanh cong");
            return ResponseEntity.ok("sucess");
        } else {
            log.info("that bai");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to set score");
        }
    }

    @GetMapping("test/updateGrade/{testRegistrationId}")
    public ResponseEntity<String> updateGrade(
            @PathVariable(required = false) Long testRegistrationId) {
        log.info("Param: " + testRegistrationId );
        if (testService.updateScoreByTestRegistrationId(testRegistrationId)) {
            log.info("thanh cong");
            return ResponseEntity.ok("sucess");
        } else {
            log.info("that bai");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to set Participated");
        }
    }

    @PostMapping("searchIMG/")
    public ResponseEntity<String> searchIMG(@RequestParam("image") MultipartFile imageFile) {
        log.info("Param: " + imageFile );
        String result=testService.searchIMG(imageFile);
        if(result!=null){
            return ResponseEntity.ok(result);

        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to find Student");
        }

    }
}
