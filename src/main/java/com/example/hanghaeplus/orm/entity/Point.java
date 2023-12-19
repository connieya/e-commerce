package com.example.hanghaeplus.orm.entity;

import com.example.hanghaeplus.orm.entity.common.BaseEntity;
import com.example.hanghaeplus.orm.vo.PointTransactionStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.hanghaeplus.orm.vo.PointTransactionStatus.*;

@Entity
@Table(name = "points")
@Getter
@NoArgsConstructor
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Long point;

    private PointTransactionStatus status;


    @Builder
    private Point(User user, Long point, PointTransactionStatus status) {
        this.user = user;
        this.point = point;
        this.status = status;
    }

    public static Point create(User user, Long point){
        return new Point(user,point, RECHARGE);
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
