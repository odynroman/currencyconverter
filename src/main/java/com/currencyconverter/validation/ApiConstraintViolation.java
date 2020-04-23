package com.currencyconverter.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.ConstraintViolation;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class ApiConstraintViolation {
    private String message;
    private String path;

    public static ApiConstraintViolation of(ConstraintViolation constraintViolation) {
        return of(constraintViolation.getMessage(), constraintViolation.getPropertyPath().toString());
    }
}