package com.example.usedcarapp.controllers;

import com.example.usedcarapp.model.Car;
import com.example.usedcarapp.services.CarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public List<Car> getAll() {
        return carService.getAll();
    }

    @GetMapping("/types")
        public List<String> getBrands() {
        return carService.getBrands();
    }

}
