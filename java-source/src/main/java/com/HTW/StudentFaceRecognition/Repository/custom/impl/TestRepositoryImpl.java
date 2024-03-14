package com.HTW.StudentFaceRecognition.Repository.custom.impl;

import com.HTW.StudentFaceRecognition.DTO.RequestDTO.TestSearchRequest;
import com.HTW.StudentFaceRecognition.Model.Entity.TestEntity;
import com.HTW.StudentFaceRecognition.Repository.custom.TestRepositoryCustom;
import com.HTW.StudentFaceRecognition.Repository.custom.StudentRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import java.util.List;
@Log4j2
@ComponentScan
@Repository
public class TestRepositoryImpl implements TestRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<TestEntity> searchTest(TestSearchRequest testSearchRequest){
        String name = testSearchRequest.getName();
        String professorName= testSearchRequest.getProfessorName();
        List<Long> courseIds = testSearchRequest.getCourseIds();
        String jpql = "SELECT DISTINCT t FROM TestEntity t WHERE 1=1";
        if (name != null && !name.trim().isEmpty()) {
            jpql += " AND t.title LIKE :name";
        }
        if (professorName != null && !professorName.trim().isEmpty()) {
            jpql += " AND t.course.professor.name LIKE :professorName";
        }

        if (courseIds != null && !courseIds.isEmpty()) {
            jpql += " AND t.course.courseId IN :courseIds";
        }


        Query query = entityManager.createQuery(jpql, TestEntity.class);

        if (name != null && !name.trim().isEmpty()) {
            query.setParameter("name", "%" + name + "%");
        }
        if (professorName != null && !professorName.trim().isEmpty()) {
            query.setParameter("professorName", "%" + professorName + "%");
        }
        if (courseIds != null && !courseIds.isEmpty()) {
            query.setParameter("courseIds", courseIds);
        }

        log.info("JPQL Query: {}", jpql);
        return query.getResultList();
    }
}
