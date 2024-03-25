package com.example.hanghaeplus.domain.point;

import com.example.hanghaeplus.domain.user.User;
import com.example.hanghaeplus.infrastructure.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static com.example.hanghaeplus.domain.point.PointTransactionType.*;

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
    private User user;
    private Long point;

    @Enumerated(EnumType.STRING)
    private PointTransactionType pointTransaction;


    @Builder
    private PointLine(User user, Long point, PointTransactionType pointTransaction) {
        this.user = user;
        this.point = point;
        this.pointTransaction = pointTransaction;
    }

    public static PointLine create(User user, Long point) {
        return new PointLine(user, point, RECHARGE);
    }

    public static PointLine create(User user, Long point, PointTransactionType pointTransaction) {
        return new PointLine(user, point, pointTransaction);
    }

    public void setUser(User user) {
            this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointLine point1 = (PointLine) o;
        return Objects.equals(id, point1.id) && Objects.equals(user, point1.user) && Objects.equals(point, point1.point) && pointTransaction == point1.pointTransaction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, point, pointTransaction);
    }
}

