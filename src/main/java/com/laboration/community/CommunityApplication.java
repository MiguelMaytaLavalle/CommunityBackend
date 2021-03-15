package com.laboration.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.laboration.repository")
@ComponentScan(basePackages = "com.laboration.controller")
@ComponentScan(basePackages = "com.laboration.service")
@ComponentScan(basePackages = "com.laboration.controller.basicAuth")
@ComponentScan(basePackages = "com.laboration.jwt")
@EntityScan("com.laboration.models")
@SpringBootApplication
public class CommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
