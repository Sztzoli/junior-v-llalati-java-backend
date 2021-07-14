package com.example.navapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NavAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(NavAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(CaseService caseService) {
        return args -> {
            caseService.addToCase(new Case("001", "Adóbevallás"));
        };
    }

}
