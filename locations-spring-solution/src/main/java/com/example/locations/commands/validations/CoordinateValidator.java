package com.example.locations.commands.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CoordinateValidator implements ConstraintValidator<Coordinate, Double> {

    private Type type;

    @Override
    public void initialize(Coordinate constraintAnnotation) {
        type = constraintAnnotation.type();

    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext constraintValidatorContext) {
        if (type == Type.LAT) {
            return value >= Type.LAT.getMin() &&
                    value <= Type.LAT.getMax();
        } else {
            return value >= Type.LON.getMin() &&
                    value <= Type.LON.getMax();
        }
    }
}
