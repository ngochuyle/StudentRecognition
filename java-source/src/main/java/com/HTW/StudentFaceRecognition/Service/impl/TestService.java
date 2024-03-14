package com.HTW.StudentFaceRecognition.Service.impl;

import com.HTW.StudentFaceRecognition.Constant.FileConstant;
import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.TestReponseDTO;
import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.TestStudentReponse;
import com.HTW.StudentFaceRecognition.DTO.RequestDTO.TestCreateRequestDTO;
import com.HTW.StudentFaceRecognition.DTO.RequestDTO.TestSearchRequest;
import com.HTW.StudentFaceRecognition.Model.Entity.*;
import com.HTW.StudentFaceRecognition.Repository.*;
import com.HTW.StudentFaceRecognition.Service.ITestService;
import com.HTW.StudentFaceRecognition.Utils.AIUltils;
import com.HTW.StudentFaceRecognition.Utils.FileUItils;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;


import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class TestService implements ITestService {
    @Autowired
    TestRepository testRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseEnrollmentRepository courseEnrollmentRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TestRegistrationRepository testRegistrationRepository;

    @Override
    public List<TestReponseDTO> findByCustomTest(TestSearchRequest testSearchRequest) {
        List<TestEntity> testEntities = testRepository.searchTest(testSearchRequest);
        List<TestReponseDTO> testReponses = new ArrayList<>();
        if (testEntities.size() <= 0) {
            log.info("not found");
        }
        for (TestEntity te : testEntities) {
            log.info("test entity: " + te.getId() + ", " + te.getTitle());
            TestReponseDTO testReponse = TestReponseDTO.builder().id(te.getId()).title(te.getTitle()).description(te.getDescription()).coursename(te.getCourse().getName()).professor(te.getCourse().getProfessor().getName()).build();
            testReponses.add(testReponse);
        }
        return testReponses;
    }

    @Override
    @Transactional
    public Boolean createTest(TestCreateRequestDTO testCreateRequestDTO) {
        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(testCreateRequestDTO.getCourseId());
        if (courseEntityOptional.isPresent()) {
            CourseEntity courseEntity = courseEntityOptional.get();
            TestEntity testEntity = TestEntity.builder().title(testCreateRequestDTO.getName()).description(testCreateRequestDTO.getDescription()).course(courseEntity).build();
            testRepository.save(testEntity);
            List<CourseEnrollmentEntity> CourseEnrollmentEntities = courseEnrollmentRepository.findByCourse_CourseId(testEntity.getCourse().getCourseId());
            log.info("course size" + CourseEnrollmentEntities.size());

            for (CourseEnrollmentEntity courseEnrollmentEntity : CourseEnrollmentEntities) {
                log.info("huy");
                log.info("test course enrollnebt" + courseEnrollmentEntity);
                Optional<StudentEntity> studentEntityOptional = studentRepository.findById(courseEnrollmentEntity.getStudent().getId());
                if (studentEntityOptional.isPresent()) {
                    TestRegistrationEntity testRegistrationEntity = TestRegistrationEntity.builder().student(studentEntityOptional.get()).test(testEntity).build();
                    testRegistrationRepository.save(testRegistrationEntity);
                }
            }
            return true;

        }

        return false;
    }

    @Override
    @Transactional
    public Boolean updateTest(TestCreateRequestDTO updateTestDTO) {

        Optional<TestEntity> testEntityOptional = testRepository.findById(updateTestDTO.getId());
        if (testEntityOptional.isPresent()) {
            TestEntity testEntity = testEntityOptional.get();
            testEntity.setTitle(updateTestDTO.getName());
            testEntity.setDescription(updateTestDTO.getDescription());
            testRepository.save(testEntity);
            log.info("update sucess: " + testEntity.toString());
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public Boolean deleteTest(Long testID) {
        List<TestRegistrationEntity> testRegistrationEntities = testRegistrationRepository.findByTestId(testID);
        if (testRegistrationEntities.size() > 0) {
            for (TestRegistrationEntity testRegistrationEntity : testRegistrationEntities) {
                log.info("delete: " + testRegistrationEntity);
                testRegistrationRepository.delete(testRegistrationEntity);
            }
            testRepository.deleteById(testID);
            return true;
        }
        return false;
    }

    @Override
    public List<TestStudentReponse> getAllStudentByTestID(Long testId) {
        List<TestRegistrationEntity> testRegistrationEntities = testRegistrationRepository.findByTestId(testId);
        log.info("test regis: " + testRegistrationEntities);
        List<TestStudentReponse> testStudentReponses = new ArrayList<>();
        if (testRegistrationEntities.size() > 0) {
            for (TestRegistrationEntity testRegistrationEntity : testRegistrationEntities) {
                log.info("test regis with" + testRegistrationEntity);
                Optional<StudentEntity> studentEntityOptional = studentRepository.findById(testRegistrationEntity.getStudent().getId());
                if (studentEntityOptional.isPresent()) {
                    StudentEntity studentEntity = studentEntityOptional.get();
                    log.info("StudentEntity: " + studentEntity);
                    TestStudentReponse testStudentReponse = TestStudentReponse.builder().id(studentEntity.getId()).name(studentEntity.getName()).sNummer(studentEntity.getSNummer()).ImgURL(getURLbySnummer(studentEntity.getSNummer())).score(testRegistrationEntity.getScore()).participated(testRegistrationEntity.getParticipated()).testRegistrationId(testRegistrationEntity.getRegistrationId()).build();
                    log.info("testReponse: " + testStudentReponse);
                    testStudentReponses.add(testStudentReponse);
                }
            }
            return testStudentReponses;
        }
        return null;
    }

    @Override
    @Transactional

    public Boolean updateScoreByStudentID(Long studentID, BigDecimal score, Long testRegistrationId) {
        Optional<TestRegistrationEntity> testRegistrationEntityOptional = testRegistrationRepository.findById(testRegistrationId);
        if (testRegistrationEntityOptional.isPresent()) {
            TestRegistrationEntity testRegistrationEntity = testRegistrationEntityOptional.get();
            testRegistrationEntity.setScore(score);
            testRegistrationRepository.save(testRegistrationEntity);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean updateScoreByTestRegistrationId(Long testRegistrationId) {
        Optional<TestRegistrationEntity> testRegistrationEntityOptional = testRegistrationRepository.findById(testRegistrationId);
        if (testRegistrationEntityOptional.isPresent()) {
            TestRegistrationEntity testRegistrationEntity = testRegistrationEntityOptional.get();
            testRegistrationEntity.setParticipated("T");
            testRegistrationRepository.save(testRegistrationEntity);
            return true;
        }
        return false;
    }

    @Override
    public String searchIMG(MultipartFile imageFile) {
        try {
            FileUItils.saveImage(imageFile);
            String result=AIUltils.searchByIMG();
            if(result!="None"){
                return result;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private List<String> getURLbySnummer(String sNummer) {
        List<String> imageUrls = new ArrayList<>();
        String sNummerPath = sNummer.substring(1);
        File studentDir = new File(FileConstant.BASE_PATH + sNummerPath + "/" + FileConstant.IMAGES_DIR);
        if (studentDir.exists() && studentDir.isDirectory()) {
            File[] files = studentDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) { // make sure it's a file, not a directory
                        String imageUrl = "http://localhost:8081/images/" + sNummerPath + "/" + FileConstant.IMAGES_DIR + "/" + file.getName();
                        imageUrls.add(imageUrl);
                    }
                }
            }
        }
        return imageUrls;
    }


}
