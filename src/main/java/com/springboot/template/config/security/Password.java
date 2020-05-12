package com.springboot.template.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class Password {

    private final String passwordSecret = "jaghjdgshja8762378169ewqsohliowyd839eyh19836jhdbsutelAQkYopOLAZ/axJI37R";
    private final int passwordIterations = 12000;
    private final int passwordHashWidth = 256;

    @Bean
    public PasswordEncoder userPasswordEncoder() {
        PasswordEncoder encoder =  new Pbkdf2PasswordEncoder(passwordSecret, passwordIterations, passwordHashWidth);
        return encoder;
    }
}
