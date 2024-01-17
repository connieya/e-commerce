package com.example.hanghaeplus.repository.point;

import com.example.hanghaeplus.repository.user.UserEntity;
import com.example.hanghaeplus.repository.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static com.example.hanghaeplus.repository.point.PointTransactionStatus.*;

@Entity
@Table(name = "point_line")
@Getter
@NoArgsConstructor
public class PointLine extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private Long point;

    @Enumerated(EnumType.STRING)
    private PointTransactionStatus status;


    @Builder
    private PointLine(UserEntity user, Long point, PointTransactionStatus status) {
        this.user = user;
        this.point = point;
        this.status = status;
    }

    public static PointLine create(UserEntity user, Long point){
        return new PointLine(user,point, DEDUCT);
    }

    public static PointLine create(UserEntity user, Long point, PointTransactionStatus status){
        return new PointLine(user,point, status);
    }

    public void setUser(UserEntity user) {

        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointLine point1 = (PointLine) o;
        return Objects.equals(id, point1.id) && Objects.equals(user, point1.user) && Objects.equals(point, point1.point) && status == point1.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, point, status);
    }
}

