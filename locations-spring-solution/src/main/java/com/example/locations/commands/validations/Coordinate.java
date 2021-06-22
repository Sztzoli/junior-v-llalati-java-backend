package com.example.locations.commands.validations;


import com.example.locations.commands.validations.CoordinateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CoordinateValidator.class)
public @interface Coordinate {

    Type type();

    String message() default "Invalid coordinate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
