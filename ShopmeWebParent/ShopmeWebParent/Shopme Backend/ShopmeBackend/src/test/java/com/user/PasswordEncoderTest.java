package com.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {
    @Test
    public void passwordEncoderTest() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "StrongTower7";
        String encodedPassword = passwordEncoder.encode(password);

        System.out.println(encodedPassword);

        boolean check = passwordEncoder.matches(password, encodedPassword);

        assertThat(check).isTrue();
    }
}
