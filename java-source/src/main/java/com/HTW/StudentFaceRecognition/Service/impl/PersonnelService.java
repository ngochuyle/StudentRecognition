package com.HTW.StudentFaceRecognition.Service.impl;

import com.HTW.StudentFaceRecognition.DTO.ReponseDTO.SingInReponse;
import com.HTW.StudentFaceRecognition.Model.Personnel;
import com.HTW.StudentFaceRecognition.Repository.PersonnelRepository;
import com.HTW.StudentFaceRecognition.Service.IPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PersonnelService implements IPersonnelService {
    @Autowired
    PersonnelRepository personnelRepository;
    @Override
    public SingInReponse findByName(String name) {
        List<Personnel> personnelList= personnelRepository.findByName(name);
        if(personnelList.size()>0){
            SingInReponse singInReponse = new SingInReponse();
            singInReponse.setName(personnelList.get(0).getName());
            singInReponse.setId(personnelList.get(0).getId());
            return singInReponse;
        }
        return null;
    }
}
