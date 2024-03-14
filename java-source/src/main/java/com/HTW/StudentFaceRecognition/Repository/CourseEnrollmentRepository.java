package com.HTW.StudentFaceRecognition.Repository;

import com.HTW.StudentFaceRecognition.Model.Entity.CourseEnrollmentEntity;
import com.HTW.StudentFaceRecognition.Model.Entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollmentEntity,Long> {
    void deleteByStudentId(Long studentId);
    List<CourseEnrollmentEntity> findByStudentId(Long studentID);
    List<CourseEnrollmentEntity> findByCourse_CourseId(Long courseID);

    @Modifying
    @Query("DELETE FROM CourseEnrollmentEntity c WHERE c.student.id = :studentId AND c.course.courseId = :courseID")
    void deleteByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseID") Long courseID);

}
