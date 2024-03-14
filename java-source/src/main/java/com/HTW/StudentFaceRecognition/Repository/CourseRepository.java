package com.HTW.StudentFaceRecognition.Repository;

import com.HTW.StudentFaceRecognition.Model.Entity.CourseEntity;
import io.jsonwebtoken.ClaimJwtException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,Long> {
    @Query("SELECT c FROM CourseEntity c JOIN c.enrollments e WHERE e.student.id= :studentID")
    Set<CourseEntity> findCoursesByStudentId(@Param("studentID") Long studentID);
}
