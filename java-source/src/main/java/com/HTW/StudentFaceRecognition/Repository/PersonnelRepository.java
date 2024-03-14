package com.HTW.StudentFaceRecognition.Repository;

import com.HTW.StudentFaceRecognition.Model.Personnel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface PersonnelRepository extends CrudRepository<Personnel,Long> {
    List<Personnel> findByName(String name);

}
