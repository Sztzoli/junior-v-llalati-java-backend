package com.example.fightergame;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FighterGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(FighterGameApplication.class, args);
    }

    @Bean
    public ModelMapper Modelmapper() {
        return new ModelMapper();
    }
}
