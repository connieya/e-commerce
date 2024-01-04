package com.example.hanghaeplus.repository.point;

import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static com.example.hanghaeplus.repository.point.PointTransactionStatus.*;

@Entity
@Table(name = "points")
@Getter
@NoArgsConstructor
public class Point extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Long point;

    @Enumerated(EnumType.STRING)
    private PointTransactionStatus status;


    @Builder
    private Point(User user, Long point, PointTransactionStatus status) {
        this.user = user;
        this.point = point;
        this.status = status;
    }

    public static Point create(User user, Long point){
        return new Point(user,point, DEDUCT);
    }

    public static Point create(User user, Long point, PointTransactionStatus status){
        return new Point(user,point, status);
    }

    public void setUser(User user) {

        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point1 = (Point) o;
        return Objects.equals(id, point1.id) && Objects.equals(user, point1.user) && Objects.equals(point, point1.point) && status == point1.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, point, status);
    }
}

