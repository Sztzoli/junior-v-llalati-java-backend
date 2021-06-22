package com.example.usedcarapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private String brand;
    private String type;
    private int age;

    private List<KmState> kmStates;
}
