package com.currencyconverter.handler;

import com.currencyconverter.model.dto.ConversionRequest;
import com.currencyconverter.model.dto.ConversionResponse;
import com.currencyconverter.service.CurrencyConverter;
import com.currencyconverter.validation.ApiValidator;
import com.currencyconverter.validation.ValidationResult;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ExchangeRatesHandler {
    private final CurrencyConverter currencyConverter;
    private final ApiValidator apiValidator;

    public Mono<ServerResponse> convert(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ConversionRequest.class)
                .zipWhen(apiValidator::validate)
                .flatMap(request -> request.getT2().isValid()
                        ? convertCurrency(request.getT1())
                        : validationErrorsFlow(request.getT2()));
    }

    private Mono<ServerResponse> convertCurrency(ConversionRequest conversionRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(currencyConverter.convert(conversionRequest), ConversionResponse.class);
    }

    private static Mono<ServerResponse> validationErrorsFlow(ValidationResult validationResult) {
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validationResult);
    }
}