package com.mainul35.bsuserinfo.config.validator;

import com.mainul35.bsuserinfo.config.validator.annotation.OneOf;
import controllers.dtos.enums.Field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class OneOfValidator implements ConstraintValidator<OneOf, String> {

    private Field[] fields;
    @Override
    public void initialize(OneOf constraintAnnotation) {
        fields = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintContext) {
        return Arrays.asList(fields).contains(Field.of(fieldValue));
    }
}