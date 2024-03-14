package com.HTW.StudentFaceRecognition.Repository.custom.impl;

import com.HTW.StudentFaceRecognition.DTO.RequestDTO.StudentRequest;
import com.HTW.StudentFaceRecognition.Model.Entity.StudentEntity;

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
public class StudentRepositoryImpl implements StudentRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<StudentEntity> searchStudent(StudentRequest studentRequest) {
        String name = studentRequest.getName();
        String sNummer = studentRequest.getSNummer();
        List<Long> courseIds= studentRequest.getCourseIDs();
        String jpql = "SELECT DISTINCT s FROM StudentEntity s ";

        if (courseIds != null && courseIds.size()>0) {
            jpql += " JOIN s.enrollments e WHERE e.course.id IN :courseIds";
        }else{
            jpql += " WHERE 1=1 ";
        }

        if (name != null && !name.trim().isEmpty()) {
            jpql += " AND (s.name IS NULL OR s.name LIKE :name)";
        }

        // Thêm điều kiện tìm kiếm cho SNummer nếu có
        if (sNummer != null && !sNummer.trim().isEmpty()) {
            jpql += " AND (s.sNummer IS NULL OR s.sNummer LIKE :sNummer)";
        }
        Query query = entityManager.createQuery(jpql, StudentEntity.class);
        if (name != null && !name.trim().isEmpty()) {
            query.setParameter("name", "%" + name + "%");
        }
        if (sNummer != null && !sNummer.trim().isEmpty()) {
            query.setParameter("sNummer", "%" + sNummer + "%");
        }
        if (courseIds != null && courseIds.size()>0) {
            query.setParameter("courseIds", courseIds);
        }

        log.info(query);

        return query.getResultList();
    }
}
