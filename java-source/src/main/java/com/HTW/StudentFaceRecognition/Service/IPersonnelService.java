package com.HTW.StudentFaceRecognition.Service;

import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.SingInReponse;
import com.HTW.StudentFaceRecognition.Model.Personnel;

import java.util.Optional;

public interface IPersonnelService {

    SingInReponse findByName(String name);
}
