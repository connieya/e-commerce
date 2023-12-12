package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.orm.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "points")
@Getter
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Long point;

    public Point() {
        this.point = 0L;
    }

    public Point(Long point) {
        this.point = point;
    }

    public void recharge(Long point){
        this.point += point;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
