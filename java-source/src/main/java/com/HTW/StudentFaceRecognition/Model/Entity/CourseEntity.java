package com.HTW.StudentFaceRecognition.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter  // getters
@Setter  // setters
@NoArgsConstructor  // constructor without parameter
@AllArgsConstructor  // constructor with parameter
@Entity
@Table(name = "course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseid")
    private Long courseId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CourseEnrollmentEntity> enrollments;

    @ManyToOne(fetch = FetchType.LAZY)  // Relationship 1:n with Professors
    @JoinColumn(name = "ProfessorID")  // foreign key in Professors
    private ProfessorEntity professor;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TestEntity> tests;

}
