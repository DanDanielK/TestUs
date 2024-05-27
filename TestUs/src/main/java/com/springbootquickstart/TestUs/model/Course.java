package com.springbootquickstart.TestUs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springbootquickstart.TestUs.test.Test;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

    // @ManyToMany
    // @JoinTable(name="course_student",joinColumns = @JoinColumn(name =
    // "course_id", referencedColumnName = "id"),
    // inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName =
    // "id"))
    // private List<Student> students=new ArrayList<Student>();
    @OneToMany(mappedBy = "course")
    private List<CourseStudent> courseStudents = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacher_id")
    // @JsonIgnore
    private Teacher teacher;

    // dor: added list of tests for courses
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Test> tests = new ArrayList<>();

}
