package com.laboration.controller.basicAuth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptEncoderTest {
    public String encryptPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
        /*for(int i = 0; i <= 10; i++){
            String encodedString = encoder.encode("password");
            System.out.println(encodedString);
        }*/
    }
}
