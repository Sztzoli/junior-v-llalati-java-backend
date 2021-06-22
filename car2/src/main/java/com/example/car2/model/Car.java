package com.example.car2.model;

import java.util.List;

public class Car {

    private String brand;
    private String type;
    private int age;


    private List<KmState> kmStates;

    public Car(String brand, String type, int age,List<KmState> kmStates) {
        this.brand = brand;
        this.type = type;
        this.age = age;
        this.kmStates = kmStates;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public int getAge() {
        return age;
    }



    public List<KmState> getKmStates() {
        return kmStates;
    }
}
