package com.HTW.StudentFaceRecognition.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "testregistration")
public class TestRegistrationEntity {
    @Id
    @Column(name = "RegistrationID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int registrationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentID")
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TestID")
    private TestEntity test;

    @Column(name="Score")
    private BigDecimal score;
    @Column(name="participated")
    private String participated;

}
