package com.example.hanghaeplus.domain.user;

import com.example.hanghaeplus.infrastructure.common.BaseEntity;
import com.example.hanghaeplus.application.user.UserException;
import com.example.hanghaeplus.application.user.command.UserCreate;
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
    private String password;
    private String nickname;
    private Long point;

//    @Version
//    private Long version;



    @Builder
    private User(Long id, String name, String email, String nickname, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.point = 0L;
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
                .build();
    }

    public static User create(String name , String nickname , String email, String password){
        return User
                .builder()
                .name(name)
                .nickname(nickname)
                .email(email)
                .password(password)
                .build();
    }

    public static User create(Long id , String name , String nickname , String email , String password){
        return User
                .builder()
                .id(id)
                .name(name)
                .nickname(nickname)
                .email(email)
                .password(password)
                .build();
    }
}
