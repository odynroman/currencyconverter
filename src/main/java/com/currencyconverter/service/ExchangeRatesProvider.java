package com.currencyconverter.service;

import com.currencyconverter.model.dto.ConversionRequest;
import reactor.core.publisher.Mono;

public interface ExchangeRatesProvider {
    Mono<Double> fetchRate(ConversionRequest request);
    void setUrl(String url);
}
