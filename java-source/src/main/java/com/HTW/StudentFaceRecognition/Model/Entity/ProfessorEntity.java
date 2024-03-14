package com.HTW.StudentFaceRecognition.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter  // Tạo getters tự động cho tất cả trường
@Setter  // Tạo setters tự động cho tất cả trường
@NoArgsConstructor  // Tạo constructor không tham số
@AllArgsConstructor  // Tạo constructor với tất cả trường làm tham số
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
