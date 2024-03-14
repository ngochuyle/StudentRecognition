package com.HTW.StudentFaceRecognition.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courseenrollment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollmentEntity {
    @Id
    @Column(name="EnrollmentID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int enrollmentId;

    @ManyToOne
    @JoinColumn(name = "CourseID", referencedColumnName = "CourseID")
    private CourseEntity course;
    @ManyToOne
    @JoinColumn(name = "StudentID", referencedColumnName = "StudentID")
    private StudentEntity student;


}
