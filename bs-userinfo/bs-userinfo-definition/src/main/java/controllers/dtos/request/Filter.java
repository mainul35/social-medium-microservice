package controllers.dtos.request;

import controllers.dtos.enums.Field;
import lombok.Data;
import validator.annotation.OneOf;

import jakarta.validation.constraints.NotBlank;

@Data
public class Filter {
    @NotBlank(message = "Field value must not be blank")
    private String value;
    @OneOf(value = {Field.email, Field.username}, message = "Value must match one of the values in the list: [email, username]")
    private String field;
}