package com.example.navapp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TaxNumberValidator implements ConstraintValidator<TaxNumber, String> {
    public void initialize(TaxNumber constraint) {
    }

    public boolean isValid(String taxNumber, ConstraintValidatorContext context) {
        return taxNumber.length() == 10 &&
                !taxNumber.startsWith("0") &&
                taxNumber.chars().allMatch(Character::isDigit) &&
                cdvCheck(taxNumber)
                ;

    }

    public boolean cdvCheck(String text) {
        List<Integer> numbers = text.chars().map(Character::getNumericValue).boxed().collect(Collectors.toList());
        AtomicInteger counter = new AtomicInteger();
        Integer reduce = numbers.subList(0, numbers.size() - 1).stream().reduce(
                0,
                (preNumber, newNumber) -> preNumber + newNumber * (counter.incrementAndGet())
        );

        return reduce % 11 == numbers.get(9);
    }
}
