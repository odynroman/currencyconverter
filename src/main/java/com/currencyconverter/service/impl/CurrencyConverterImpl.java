package com.currencyconverter.service.impl;

import com.currencyconverter.model.dto.ConversionRequest;
import com.currencyconverter.model.dto.ConversionResponse;
import com.currencyconverter.service.CurrencyConverter;
import com.currencyconverter.service.ExchangeRatesProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CurrencyConverterImpl implements CurrencyConverter {
    private final ExchangeRatesProvider exchangeRatesApiComProvider;
    private final ExchangeRatesProvider exchangeRatesApiIoProvider;

    public CurrencyConverterImpl(ExchangeRatesProvider exchangeRatesApiComProvider,
                                 ExchangeRatesProvider exchangeRatesApiIoProvider,
                                 @Value("${exchangeApi.exchangeRatesApiIo.url}") String exchangeRatesApiIoUrl,
                                 @Value("${exchangeApi.exchangeRateApiCom.url}") String exchangeRateApiComUrl) {
        this.exchangeRatesApiComProvider = exchangeRatesApiComProvider;
        this.exchangeRatesApiComProvider.setUrl(exchangeRateApiComUrl);

        this.exchangeRatesApiIoProvider = exchangeRatesApiIoProvider;
        this.exchangeRatesApiIoProvider.setUrl(exchangeRatesApiIoUrl);

    }

    @Override
    public Mono<ConversionResponse> convert(ConversionRequest conversionRequest) {
        return exchangeRatesApiComProvider.fetchRate(conversionRequest)
                .or(exchangeRatesApiIoProvider.fetchRate(conversionRequest))
                .map(rate ->
                        ConversionResponse.builder()
                                .from(conversionRequest.getFrom())
                                .to(conversionRequest.getTo())
                                .amount(conversionRequest.getAmount())
                                .converted(rate * conversionRequest.getAmount())
                                .build()
                );
    }
}
