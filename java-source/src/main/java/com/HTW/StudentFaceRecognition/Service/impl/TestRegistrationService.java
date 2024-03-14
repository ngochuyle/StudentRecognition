package com.HTW.StudentFaceRecognition.Service.impl;

import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.TestReponse;
import com.HTW.StudentFaceRecognition.Model.Entity.StudentEntity;
import com.HTW.StudentFaceRecognition.Model.Entity.TestEntity;
import com.HTW.StudentFaceRecognition.Model.Entity.TestRegistrationEntity;
import com.HTW.StudentFaceRecognition.Repository.StudentRepository;
import com.HTW.StudentFaceRecognition.Repository.TestRegistrationRepository;
import com.HTW.StudentFaceRecognition.Repository.TestRepository;
import com.HTW.StudentFaceRecognition.Service.ITestRegistrationService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Log4j2
@Service
public class TestRegistrationService implements ITestRegistrationService {
    @Autowired
    TestRegistrationRepository testRegistrationRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TestRepository testRepository;
    @Override
    public List<TestReponse> findTestByStudentID(Long studentId){
        List<TestReponse> result = new ArrayList<>();
        List<Object[]> testObjects = testRegistrationRepository.findTestsByStudentId(studentId);
        if(testObjects.size()>0){
            for (Object[] details : testObjects) {
                TestEntity testEntities = (TestEntity) details[0];
                BigDecimal score = (BigDecimal) details[1];
                String participated = (String) details[2];
                TestReponse testReponse = TestReponse.builder()
                        .id(testEntities.getId())
                        .description(testEntities.getDescription())
                        .coursename(testEntities.getCourse().getName())
                        .professor(testEntities.getCourse().getProfessor().getName())
                        .title(testEntities.getTitle())
                        .score(score)
                        .participated(participated)
                        .build();
                result.add(testReponse);
                // Xử lý hoặc in ra thông tin
                log.info("Test: " + testEntities.getTitle() + ", Score: " + score + ", Participated: " + participated);
            }
            return  result;
        }
        return null;
    }
    @Transactional
    public void registerStudentForAllTestsInCourse(Long studentId, Long courseId) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        List<TestEntity> tests = testRepository.findByCourseId(courseId);

        for (TestEntity test : tests) {
            TestRegistrationEntity testRegistration = new TestRegistrationEntity();
            testRegistration.setStudent(student);
            testRegistration.setTest(test);

            testRegistrationRepository.save(testRegistration);
        }
    }
    @Transactional
    public void deleteStudentForAllTestsInCourse(Long studentId, Long courseId) {
        log.info("xoa cai na nyaf");
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        log.info("student"+student.toString());
        List<TestEntity> tests = testRepository.findByCourseId(courseId);
        for (TestEntity test : tests) {
            testRegistrationRepository.deleteByStudentIDAndTestID(studentId,test.getId());
        }
    }

}
