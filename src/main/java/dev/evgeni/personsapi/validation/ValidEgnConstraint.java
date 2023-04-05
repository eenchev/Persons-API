package dev.evgeni.personsapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEgnConstraint implements ConstraintValidator<ValidEgn, String> {

    @Override
    public boolean isValid(String egnValue, ConstraintValidatorContext context) {
        if (egnValue == null || egnValue.length() != 10) {
            return false;
        }
        return true;
    }

}
