package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.orm.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;

    private Long currentPoint;


    public User(String name) {
        this.name = name;
    }

    @Builder
    private User(Long id, String name, Long currentPoint) {
        this.id = id;
        this.name = name;
        this.currentPoint = currentPoint;
    }

    @Builder
    private User(String name, Long currentPoint) {
        this.name = name;
        this.currentPoint = currentPoint;
    }


    public void deductPoints(Long point) {
        this.currentPoint -= point;
    }

    public static User create(String name , Long currentPoint){
        return User.builder()
                .name(name)
                .currentPoint(currentPoint)
                .build();
    }

}
