package com.HTW.StudentFaceRecognition.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StudentID")
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "SNummer")
    private String sNummer;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CourseEnrollmentEntity> enrollments;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TestRegistrationEntity> testRegistrationEntities;
}
