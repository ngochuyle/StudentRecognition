package com.HTW.StudentFaceRecognition.Service.impl;

import com.HTW.StudentFaceRecognition.Constant.FileConstant;
import com.HTW.StudentFaceRecognition.Converter.StudentRequestToEntity;
import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.StudentReponse;
import com.HTW.StudentFaceRecognition.DTO.RequestDTO.StudentRequest;
import com.HTW.StudentFaceRecognition.Model.Entity.CourseEnrollmentEntity;
import com.HTW.StudentFaceRecognition.Model.Entity.CourseEntity;
import com.HTW.StudentFaceRecognition.Model.Entity.StudentEntity;
import com.HTW.StudentFaceRecognition.Model.Entity.TestEntity;
import com.HTW.StudentFaceRecognition.Repository.*;
import com.HTW.StudentFaceRecognition.Service.IStudentService;
import com.HTW.StudentFaceRecognition.Utils.AIUltils;
import com.HTW.StudentFaceRecognition.Utils.FileUItils;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class StudentService implements IStudentService {
    @Autowired
    StudentRepository studentReposioty;
    @Autowired
    CourseEnrollmentRepository courseEnrollmentRepository;
    @Autowired
    TestRegistrationRepository testRegistrationRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TestRepository testRepository;
    @Autowired
    TestRegistrationService testRegistrationService;
    public static String findStudentByIMG(String imagePath, String pklPath, String pythonScriptPath) throws IOException, InterruptedException {
        String[] cmd = {
                "python",  // Hoặc 'python3' tùy thuộc vào môi trường của bạn
                pythonScriptPath,  // Đường dẫn đến script Python
                imagePath,         // Đối số thứ nhất cho script: đường dẫn ảnh
                pklPath            // Đối số thứ hai cho script: đường dẫn file .pkl
        };

        // Tạo và thực thi quá trình với ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process process = pb.start();

        // Đọc output từ script Python và chỉ lưu dòng cuối cùng
        BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String lastLine = "";
        while ((line = bfr.readLine()) != null) {
            lastLine = line;
        }

        // Đợi quá trình kết thúc và lấy mã kết thúc
        int exitCode = process.waitFor();
        System.out.println("Exited with code: " + exitCode);

        return lastLine;  // Trả về ID của sinh viên hoặc "none"
    }

    @Override
    public StudentReponse findStudentByID(Long id) {
        List<String> imageUrls = new ArrayList<>();
        if (studentReposioty.findById(id).isPresent()) {
            StudentEntity studentEntity = studentReposioty.findById(id).get();
            String sNummer = studentEntity.getSNummer();
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

            StudentReponse result = StudentReponse.builder()
                    .id(studentEntity.getId())
                    .name(studentEntity.getName())
                    .sNummer(studentEntity.getSNummer())
                    .ImgURL(imageUrls)
                    .build();
            log.info("Student Request: " + result.toString());
            return result;
        }
        return null;
    }

    @Transactional
    public void updateStudentCourses(Long studentID, List<Long> newCourseIds) {
        // Lấy danh sách khóa học hiện tại
        List<CourseEnrollmentEntity> currentEnrollments = courseEnrollmentRepository.findByStudentId(studentID);

        List<Long> currentCourseIds = new ArrayList<>();
        for(CourseEnrollmentEntity courseEnrollmentEntity:currentEnrollments){
            currentCourseIds.add(courseEnrollmentEntity.getCourse().getCourseId());
        }

        // Xác định khóa học cần xóa
        List<Long> toBeRemoved = new ArrayList<>();
        for (Long currentCourseId : currentCourseIds) {
            if (!newCourseIds.contains(currentCourseId)) {
                toBeRemoved.add(currentCourseId);
            }
        }


        // Xác định khóa học mới cần thêm
        List<Long> toBeAdded = new ArrayList<>();
        for (Long newCourseId : newCourseIds) {
            if (!currentCourseIds.contains(newCourseId)) {
                toBeAdded.add(newCourseId);
            }
        }
        // xóa
        for (Long courseId : toBeRemoved) {
            courseEnrollmentRepository.deleteByStudentIdAndCourseId(studentID,courseId);
            log.info("vao vong xo 1");
            testRegistrationService.deleteStudentForAllTestsInCourse(studentID,courseId);
        }
        // thêm
        for(Long courseId: toBeAdded){
            StudentEntity student = studentReposioty.findById(studentID)
                    .orElseThrow(() -> new EntityNotFoundException("Student not found"));
            CourseEntity course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new EntityNotFoundException("Course not found"));
            CourseEnrollmentEntity courseEnrollmentEntity = CourseEnrollmentEntity.builder()
                    .course(course)
                    .student(student)
                    .build();
            courseEnrollmentRepository.save(courseEnrollmentEntity);
            testRegistrationService.registerStudentForAllTestsInCourse(studentID,courseId);
        }
    }
    @Override
    @Transactional
    public Boolean updateStudent(StudentRequest studentRequest) {
        Optional<StudentEntity> studentEntityOp = studentReposioty.findById(studentRequest.getId());
        if(studentEntityOp.isPresent()){
            StudentEntity studentEntity = studentEntityOp.get();
            studentEntity.setName(studentRequest.getName());
            studentEntity.setSNummer(studentRequest.getSNummer());
            studentReposioty.save(studentEntity);
            return true;
        }
        return false;
    }
    public Boolean updateIMG(StudentRequest studentRequest) throws IOException, InterruptedException {
        Optional<String> studentEntityOptional= studentReposioty.findSNumberById(studentRequest.getId());
        if(studentEntityOptional.isPresent()){
            String sNummer = studentEntityOptional.get();
            if (sNummer == null || sNummer.isEmpty() || !sNummer.startsWith("s")) {
                log.info("sNummer not valid");
                return false;
            }
            createFileByPath(studentRequest,sNummer);
            log.info("Snummer: "+sNummer.substring(1));
            AIUltils.deleteByNummerPy(sNummer.substring(1));
            String imagePath = FileConstant.BASE_PATH + sNummer.substring(1) + "/OriginalImages";
            String pklPath = FileConstant.EMBEDDINGS_PKL_PATH;
            String studentID =  sNummer.substring(1);
            String pythonPath = FileConstant.PYTHON_Face_Reconition_PATH;
            log.info("imagePath" + imagePath);
            log.info("pklPath" + pklPath);
            log.info("studentID" + studentID);
            log.info("pythonPath" + pythonPath);
            try {
                AIUltils.trainModel(imagePath, pklPath, studentID, pythonPath);
                log.info("Thanh cong");

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        return false;
    }
    @Override
    @Transactional
    public Boolean createStudent(StudentRequest studentRequest) {
        String sNummer = studentRequest.getSNummer();
        Optional<StudentEntity> studentEntityOptional = studentReposioty.getBySNummer(studentRequest.getSNummer());
        if(studentEntityOptional.isPresent()) {
            log.info("Da trung");
            return  false;
        }

        // Check the sNummer is valid and create a path for the directory
        if (sNummer == null || sNummer.isEmpty() || !sNummer.startsWith("s")) {
            log.info("sNummer not valid");
            return false;
        }
        createFile(studentRequest);
        String imagePath = FileConstant.BASE_PATH + studentRequest.getSNummer().substring(1) + "/OriginalImages";
        String pklPath = FileConstant.EMBEDDINGS_PKL_PATH;
        String studentID = studentRequest.getSNummer().substring(1);
        String pythonPath = FileConstant.PYTHON_Face_Reconition_PATH;
        log.info("imagePath" + imagePath);
        log.info("pklPath" + pklPath);
        log.info("studentID" + studentID);
        log.info("pythonPath" + pythonPath);
        try {
            AIUltils.trainModel(imagePath, pklPath, studentID, pythonPath);
            log.info("sucess");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            StudentEntity studentEntity = StudentRequestToEntity.convertToEntity(studentRequest);
            studentReposioty.save(studentEntity);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error when saving student information: ", e);
        }
    }

    @Override
    public List<StudentReponse> findByCustomStudent(StudentRequest studentRequest) {
        String name = null;
        if (studentRequest.getName() != null && !studentRequest.getName().isEmpty()) {
            name = "%" + studentRequest.getName() + "%";
        }
        String sNummer = null;
        if (studentRequest.getSNummer() != null && !studentRequest.getSNummer().isEmpty()) {
            name = "%" + studentRequest.getSNummer() + "%";
        }

        List<StudentEntity> studentEntities = studentReposioty.findByCustomStudent(name, sNummer, studentRequest.getCourseIDs());
        List<StudentReponse> studentReponses = new ArrayList<>();
        if (studentEntities.size() > 0) {
            for (StudentEntity se : studentEntities) {
                StudentReponse studentReponse = StudentReponse.builder()
                        .id(se.getId())
                        .name(se.getName())
                        .sNummer(se.getSNummer())
                        .ImgURL(getURLbySnummer(se.getSNummer()))
                        .build();
                studentReponses.add(studentReponse);
            }
        } else {
            log.info("does not exist");
        }
        return studentReponses;
    }

    @Override
    @Transactional
    public Boolean deleteStudentByID(Long id) {

        try {
            if (!studentReposioty.existsById(id)) {
                log.info("Deletion attempt for non-existent student ID: {}", id);
                return false;
            }
            log.info("delete test");
            // Delete related records from TestRegistration and CourseEnrollment
            testRegistrationRepository.deleteByStudentId(id);
            log.info("delete course");
            courseEnrollmentRepository.deleteByStudentId(id);
            FileUItils.deleteDirectory(FileConstant.BASE_PATH + studentReposioty.findSNumberById(id).get().substring(1));
            log.info("delete: " + FileConstant.BASE_PATH + studentReposioty.findSNumberById(id).get());
            AIUltils.deleteByNummerPy(studentReposioty.findSNumberById(id).get().substring(1));
            // delete Student
            studentReposioty.deleteById(id);

            if (!studentReposioty.existsById(id)) {

                log.info("Student with ID: {} has been successfully deleted.", id);
                return true;
            } else {
                log.error("Failed to delete student with ID: {}.", id);
                return false;
            }
        } catch (Exception e) {
            log.error("An error occurred while deleting student with ID: {}: {}", id, e.getMessage());
            return false;
        }
    }

    @Override
    public List<StudentReponse> searchStudent(StudentRequest studentRequest) {
        List<StudentEntity> studentEntities = studentReposioty.searchStudent(studentRequest);
        List<StudentReponse> studentReponses = new ArrayList<>();
        if (studentEntities.size() > 0) {
            for (StudentEntity se : studentEntities) {
                StudentReponse studentReponse = StudentReponse.builder()
                        .id(se.getId())
                        .name(se.getName())
                        .sNummer(se.getSNummer())
                        .ImgURL(getURLbySnummer(se.getSNummer()))
                        .build();
                studentReponses.add(studentReponse);
            }
        } else {
            log.info("does not exist");
        }
        log.info("studentReponses" + studentReponses);
        return studentReponses;
    }

    String getNummerVonSNummer(String sNummer) {
        return sNummer.substring(1);
    }

    boolean createFile(StudentRequest studentRequest) {
        String nummer = getNummerVonSNummer(studentRequest.getSNummer()); // Leave out the first 's'
        log.info("snummer: " + nummer);
        Path studentDirPath = Paths.get(FileConstant.BASE_PATH + nummer);
        log.info("studentDirPath: " + studentDirPath);
        Path originalImagesDirPath = studentDirPath.resolve("OriginalImages");
        log.info("orginal:  " + originalImagesDirPath);
        try {
            // Create a directory if it doesn't exist
            Files.createDirectories(originalImagesDirPath);

            int imageCounter = 1;
            for (MultipartFile image : studentRequest.getImages()) {
                if (image.isEmpty()) {
                    continue;
                }
                String imageFileName = String.format("%s_original_%d.jpg", nummer, imageCounter++);
                Path imagePath = originalImagesDirPath.resolve(imageFileName);

                // Img Folder save
                Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }
    boolean createFileByPath(StudentRequest studentRequest,String Snummer) {
        String nummer = getNummerVonSNummer(Snummer); // Leave out the first 's'
        FileUItils.deleteDirectory(FileConstant.BASE_PATH + nummer);
        log.info("snummer: " + nummer);
        Path studentDirPath = Paths.get(FileConstant.BASE_PATH + nummer);
        log.info("studentDirPath: " + studentDirPath);
        Path originalImagesDirPath = studentDirPath.resolve("OriginalImages");
        log.info("orginal:  " + originalImagesDirPath);
        try {
            Files.createDirectories(originalImagesDirPath);

            int imageCounter = 1;
            for (MultipartFile image : studentRequest.getImages()) {
                if (image.isEmpty()) {
                    continue;
                }

                String imageFileName = String.format("%s_original_%d.jpg", nummer, imageCounter++);
                Path imagePath = originalImagesDirPath.resolve(imageFileName);


                Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            }

            return true;
        } catch (IOException e) {

            return false;
        }
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
