package com.example.hanghaeplus.repository.user;

import com.example.hanghaeplus.domain.user.User;
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
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String nickname;

    private Long currentPoint;

    @Version
    private Long version;

    public UserEntity(String name) {
        this.name = name;
    }

    @Builder
    private UserEntity(Long id, String name, Long currentPoint) {
        this.id = id;
        this.name = name;
        this.currentPoint = currentPoint;
    }

    @Builder
    private UserEntity(String name, Long currentPoint) {
        this.name = name;
        this.currentPoint = currentPoint;
    }

    public static UserEntity from(User user){
        UserEntity userEntity = new UserEntity();
        userEntity.name = user.getName();
        userEntity.email = user.getEmail();
        userEntity.nickname = user.getNickname();
        userEntity.currentPoint = user.getPoint();
        return userEntity;
    }

    public User toDomain(){
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .nickname(nickname)
                .point(currentPoint)
                .build();
    }




}
