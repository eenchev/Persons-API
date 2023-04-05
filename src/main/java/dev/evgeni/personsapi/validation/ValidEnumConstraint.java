package dev.evgeni.personsapi.validation;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEnumConstraint implements ConstraintValidator<ValidEnum ,String> {
    List<String> valueList = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return valueList.contains(value.toUpperCase());
    }

    @Override
    public void initialize(ValidEnumÍ constraintAnnotation) {
        valueList = new ArrayList<String>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }
}
