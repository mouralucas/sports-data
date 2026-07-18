package com.rolf.sports_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.rolf.sports_data.repositories")
public class SportsDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsDataApplication.class, args);
    }

}
