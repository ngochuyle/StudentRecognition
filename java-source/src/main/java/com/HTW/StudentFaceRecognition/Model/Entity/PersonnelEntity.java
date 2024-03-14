package com.HTW.StudentFaceRecognition.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor // Tạo constructor không tham số
@AllArgsConstructor // Tạo constructor với tất cả tham số
public class PersonnelEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int id;
    private String name;
    private String pwd;
    private String role;
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private Set<TestEntity> createdTests = new HashSet<>();
}
