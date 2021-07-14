package com.example.navapp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InternalTimeValidator implements ConstraintValidator<InternalTime, AppointmentCommand> {

    @Override
    public boolean isValid(AppointmentCommand command, ConstraintValidatorContext constraintValidatorContext) {
        return command.getStartTime().isBefore(command.getEndTime());
    }

    @Override
    public void initialize(InternalTime constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
