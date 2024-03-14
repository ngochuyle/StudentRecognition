package com.HTW.StudentFaceRecognition.Repository;

import com.HTW.StudentFaceRecognition.Model.Entity.TestEntity;
import com.HTW.StudentFaceRecognition.Model.Entity.TestRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRegistrationRepository extends JpaRepository<TestRegistrationEntity,Long> {
    //List<TestRegistrationRepository> findByTest_Course_Id(Long testID);
    List<TestRegistrationEntity> findByTestId(Long testId);

    void deleteByStudentId(Long studentID);
    @Query("SELECT tr.test,tr.score,tr.participated FROM TestRegistrationEntity tr WHERE tr.student.id = :studentId")
    List<Object[]> findTestsByStudentId(Long studentId);
    //@Modifying
    //@Query("DELETE FROM TestRegistrationEntity t WHERE t.student.id = :studentId AND t.test.course.courseId = :courseID")
    //void deleteByStudentIDAndCourseID(@Param("studentId") Long studentId, @Param("courseID") Long courseID);
    @Modifying
    @Query("DELETE FROM TestRegistrationEntity t WHERE t.student.id = :studentId AND t.test.id = :testID")
    void deleteByStudentIDAndTestID(@Param("studentId") Long studentId, @Param("testID") Long testID);
}
