package com.springbootquickstart.TestUs.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private MyUser myUser;

    @OneToMany(mappedBy = "student")
    private List<CourseStudent> courseStudents = new ArrayList<>();


}
