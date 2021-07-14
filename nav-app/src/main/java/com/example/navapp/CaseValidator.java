package com.example.navapp;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CaseValidator implements ConstraintValidator<CaseType, String> {

    @Autowired
    CaseService service;

    public void initialize(CaseType constraint) {
    }

    public boolean isValid(String code, ConstraintValidatorContext context) {

        return service.hasCaseByCode(code);
    }
}
