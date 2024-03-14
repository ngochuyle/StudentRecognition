package com.HTW.StudentFaceRecognition.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "professor")
public class ProfessorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProfessorID")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Department")
    private String department;

    // Mối quan hệ một-đến-nhiều với Courses
    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private Set<CourseEntity> courses;

}
