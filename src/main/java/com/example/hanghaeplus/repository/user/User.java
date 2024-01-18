package com.example.hanghaeplus.repository.user;

import com.example.hanghaeplus.repository.common.BaseEntity;
import com.example.hanghaeplus.service.user.UserException;
import com.example.hanghaeplus.service.user.request.UserCreate;
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
    private String email;
    private String nickname;
    private Long point;

//    @Version
//    private Long version;



    @Builder
    private User(Long id, String name, String email, String nickname, Long point) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.point = point;
    }

    public void rechargePoint(Long point) {
        this.point += point;
    }


    public void deductPoints(Long totalPrice) {
        if (this.point < totalPrice) {
            throw new UserException.InsufficientPointsException(INSUFFICIENT_POINT);
        }
        this.point -= totalPrice;
    }

    public static User create(UserCreate userCreate) {
        return User.builder()
                .name(userCreate.getName())
                .email(userCreate.getEmail())
                .nickname(userCreate.getNickname())
                .point(userCreate.getPoint())
                .build();
    }
}
