package com.example.car2.model;

import java.time.LocalDate;

public class KmState {

    private LocalDate date;
    private int km;

    public KmState(LocalDate date, int km) {
        this.date = date;
        this.km = km;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getKm() {
        return km;
    }
}
