package com.example.usedcarapp.services;

import com.example.usedcarapp.model.Car;
import com.example.usedcarapp.model.KmState;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private List<Car> cars = new ArrayList<>(List.of(
            new Car("Suzuki", "Swift", 20,
                    new ArrayList<>(List.of
                            (new KmState(LocalDate.of(2000, Month.DECEMBER, 1), 100543),
                                    (new KmState(LocalDate.of(2001, Month.DECEMBER, 1), 4004040))))
            ),
            new Car("Opel", "astra", 13,
                    new ArrayList<>(List.of
                            (new KmState(LocalDate.of(2017, Month.DECEMBER, 1), 50000),
                                    (new KmState(LocalDate.of(2020, Month.DECEMBER, 1), 50001)))))));


    public List<Car> getAll() {
        return cars;
    }

    public List<String> getBrands() {
        return cars.stream().map(Car::getBrand).distinct().collect(Collectors.toList());
    }
}
