package com.currencyconverter.service;

import com.currencyconverter.model.dto.ConversionRequest;
import com.currencyconverter.model.dto.ConversionResponse;
import reactor.core.publisher.Mono;

public interface CurrencyConverter {
  Mono<ConversionResponse> convert(ConversionRequest conversionRequest);
}
