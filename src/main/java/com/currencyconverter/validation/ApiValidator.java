package com.currencyconverter.validation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@AllArgsConstructor
@Component
public class ApiValidator {
    private final Validator validator;

    private Stream<ApiConstraintViolation> getErrors(Object bean, Set<String> exclude) {
        return validator
                .validate(bean)
                .stream()
                .map(ApiConstraintViolation::of)
                .filter(e -> !exclude.contains(e.getPath()));
    }

    private Mono<ValidationResult> validate(Object bean, Set<String> exclude) {
        return Mono.just(ValidationResult.of(getErrors(bean, exclude).collect(Collectors.toList())));
    }

    public Mono<ValidationResult> validate(Object bean) {
        return validate(bean, Collections.emptySet());
    }
}
