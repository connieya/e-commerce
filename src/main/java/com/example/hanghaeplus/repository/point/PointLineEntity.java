package com.example.hanghaeplus.repository.point;

import com.example.hanghaeplus.domain.point.PointLine;
import com.example.hanghaeplus.domain.user.User;
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
public class PointLineEntity extends BaseEntity {


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


    public void setUser(UserEntity user) {
        this.user = user;
    }

    public static PointLineEntity from(PointLine pointLine){
        PointLineEntity pointLineEntity = new PointLineEntity();
        pointLineEntity.user = UserEntity.from(pointLine.getUser());
        pointLineEntity.point = pointLine.getPoint();
        pointLineEntity.status = pointLine.getStatus();
        return pointLineEntity;
    }

    public PointLine toDomain() {
        return PointLine.builder()
                .id(id)
                .point(point)
                .status(status)
                .user(user.toDomain())
                .build();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointLineEntity point1 = (PointLineEntity) o;
        return Objects.equals(id, point1.id) && Objects.equals(user, point1.user) && Objects.equals(point, point1.point) && status == point1.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, point, status);
    }
}

