package com.springbootquickstart.TestUs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course_student")
public class CourseStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // Enum to represent signup status
    public enum SignupStatus {
        APPROVED, WAITING, DENIED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "signup_status")
    private SignupStatus signupStatus;

    // Constructors, getters, and setters
}
