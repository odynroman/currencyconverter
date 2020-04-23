package com.currencyconverter.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor(staticName = "of")
public class ValidationResult {
    private Collection<ApiConstraintViolation> errors;
    public boolean isValid(){
        return errors.isEmpty();
    }
}
