package com.example.hanghaeplus.infrastructure.user;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean matches(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
