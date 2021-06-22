package com.example.locations.commands.validations;

public enum Type {
    LAT(-90, 90),
    LON(-180, 180);

    private double min;
    private double max;

    Type(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
