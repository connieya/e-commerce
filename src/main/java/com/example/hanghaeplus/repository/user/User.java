package com.example.hanghaeplus.repository.user;

import com.example.hanghaeplus.repository.common.BaseEntity;
import com.example.hanghaeplus.service.user.UserException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.hanghaeplus.common.error.ErrorCode.INSUFFICIENT_POINT;

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

//    @Version
//    private Long version;

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

    public void rechargePoint(Long point) {
        this.currentPoint += point;
    }


    public void deductPoints(Long totalPrice) {
        if (this.currentPoint < totalPrice) {
            throw new UserException.InsufficientPointsException(INSUFFICIENT_POINT);
        }
        this.currentPoint -= totalPrice;
    }

    public static User create(String name, Long currentPoint) {
        return User.builder()
                .name(name)
                .currentPoint(currentPoint)
                .build();
    }
}
