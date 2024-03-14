package com.HTW.StudentFaceRecognition.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter  // Tạo getters tự động cho tất cả trường
@Setter  // Tạo setters tự động cho tất cả trường
@NoArgsConstructor  // Tạo constructor không tham số
@AllArgsConstructor  // Tạo constructor với tất cả trường làm tham số
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

    @ManyToOne(fetch = FetchType.LAZY)  // Mối quan hệ nhiều-đến-một với Professors
    @JoinColumn(name = "ProfessorID")  // Khóa ngoại tham chiếu tới Professors
    private ProfessorEntity professor;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TestEntity> tests;

}
