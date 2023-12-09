package com.example.hanghaeplus.orm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Point {

    @Id
    private Long id;

    @OneToOne
    private User user;
    private Long point;


}
