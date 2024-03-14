package com.HTW.StudentFaceRecognition.Repository;

import com.HTW.StudentFaceRecognition.Model.Entity.TestEntity;
import com.HTW.StudentFaceRecognition.Repository.custom.TestRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestRepository extends JpaRepository<TestEntity,Long>, TestRepositoryCustom {
    @Query("SELECT t FROM TestEntity t WHERE t.course.courseId = :courseId")
    List<TestEntity> findByCourseId(Long courseId);
}
