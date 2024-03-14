package com.HTW.StudentFaceRecognition.Repository;

import com.HTW.StudentFaceRecognition.Model.Entity.StudentEntity;
import com.HTW.StudentFaceRecognition.Repository.custom.StudentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,Long>, StudentRepositoryCustom {
    Optional<StudentEntity> findById(Long id);
    @Query("SELECT s.sNummer FROM StudentEntity s WHERE s.id = :id")
    Optional<String> findSNumberById(@Param("id") Long id);
    @Query("SELECT s FROM StudentEntity s LEFT JOIN s.enrollments ce WHERE (:name IS NULL OR s.name LIKE :name) " +
            "AND (:sNummer IS NULL OR s.sNummer LIKE :sNummer) " +
            "AND (:courseIds IS NULL OR ce.course.courseId IN :courseIds)")
    List<StudentEntity> findByCustomStudent(@Param("name") String name,
                                       @Param("sNummer") String sNummer,
                                       @Param("courseIds") List<Long> courseIds);
    @Query("SELECT s FROM StudentEntity s WHERE s.sNummer = :sNummer")
    Optional<StudentEntity> getBySNummer(String sNummer);
}
