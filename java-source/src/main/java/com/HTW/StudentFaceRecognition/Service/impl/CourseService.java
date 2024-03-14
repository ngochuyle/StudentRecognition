package com.HTW.StudentFaceRecognition.Service.impl;

import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.CourseReponse;
import com.HTW.StudentFaceRecognition.Model.Entity.CourseEntity;
import com.HTW.StudentFaceRecognition.Repository.CourseRepository;
import com.HTW.StudentFaceRecognition.Service.ICourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Log4j2
@Service
public class CourseService implements ICourseService {
    @Autowired
    CourseRepository courseRepository;
    @Override
    public Set<CourseReponse> getCourseByStudentID(Long studentId) {
        Set<CourseReponse> result = new  HashSet <>();
        Set<CourseEntity> courseEntites= courseRepository.findCoursesByStudentId(studentId);
        if(courseEntites.size()<=0){
            log.info("no course be found");
        }else{
            for(CourseEntity courseEntity:courseEntites){
                log.info(courseEntity);
                CourseReponse courseReponse = CourseReponse.builder()
                        .courseId(courseEntity.getCourseId())
                        .name(courseEntity.getName())
                        .description(courseEntity.getDescription()).build();
                result.add(courseReponse);
            }
        }
        return result;
    }

    @Override
    public List<CourseReponse> getAllCourse() {
        List<CourseEntity> courseEntities = courseRepository.findAll();
        List<CourseReponse> courseReponses = new ArrayList<>();
        if(courseEntities.size()>0){
            log.info("Return List Course");
            for(CourseEntity courseEntity : courseEntities){
                CourseReponse courseReponse = CourseReponse.builder()
                        .courseId(courseEntity.getCourseId())
                        .name(courseEntity.getName())
                        .description(courseEntity.getDescription()).build();
                courseReponses.add(courseReponse);
            }
        }else {
            log.info("List Course is empty");
        }
        return courseReponses;
    }
}
