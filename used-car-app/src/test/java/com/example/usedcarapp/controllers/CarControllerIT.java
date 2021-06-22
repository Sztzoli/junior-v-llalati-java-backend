package com.example.usedcarapp.controllers;

import com.example.usedcarapp.contollers.CarController;
import com.example.usedcarapp.model.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
public class CarControllerIT {

    @Autowired
    CarController carController;



    @Test
    void getCars() {
         List<Car> cars = carController.getAll();

        assertThat(cars)
                .hasSize(2)
                .extracting(Car::getBrand, Car::getType)
                .contains(tuple("Opel","astra"),
                        tuple("Suzuki","Swift"));
    }

    @Test
    void  getBrands() {
        List<String> brands = carController.getBrands();

        assertThat(brands)
                .hasSize(2)
                .contains("Opel","Suzuki");
    }
}
