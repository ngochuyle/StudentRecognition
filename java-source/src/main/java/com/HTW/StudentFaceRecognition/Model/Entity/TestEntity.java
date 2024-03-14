package com.HTW.StudentFaceRecognition.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="test")
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TestID")
    private Long id;
    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CourseID") // Tên cột khóa ngoại liên kết đến bảng course
    private CourseEntity course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CreatorPersonnelID") // Tên cột khóa ngoại liên kết đến bảng personnel
    private PersonnelEntity creator;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TestRegistrationEntity> testRegistrations;

}
