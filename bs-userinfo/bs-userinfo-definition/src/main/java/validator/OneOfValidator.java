package validator;

import controllers.dtos.enums.Field;
import validator.annotation.OneOf;

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