package com.fdu.mockinterview;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
    @Test
    void bCryptPasswordTest(){
        PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
        String rawPassword = "123456";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("raw pwd:" + rawPassword);
        System.out.println("hashed pwd:" + encodedPassword);

        System.out.println(rawPassword + " matches " + encodedPassword + ":"
                + passwordEncoder.matches(rawPassword, encodedPassword));

        System.out.println("654321 matches" + encodedPassword + ":"
                + passwordEncoder.matches("654321", encodedPassword));
    }
}
