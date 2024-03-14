package com.HTW.StudentFaceRecognition.Repository.custom;

import com.HTW.StudentFaceRecognition.DTO.RequestDTO.TestSearchRequest;
import com.HTW.StudentFaceRecognition.Model.Entity.TestEntity;

import java.util.List;

public interface TestRepositoryCustom {

    List<TestEntity> searchTest(TestSearchRequest testSearchRequest);
}
