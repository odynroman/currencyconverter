package com.currencyconverter.service.impl;

import com.currencyconverter.model.dto.ConversionRequest;
import com.currencyconverter.model.dto.ExchangeRatesApiResponse;
import com.currencyconverter.service.ExchangeRatesProvider;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Setter
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DefaultExchangeRatesProviderImpl implements ExchangeRatesProvider {
    private final WebClient exchangeRateApiClient;
    private String url;

    public DefaultExchangeRatesProviderImpl(WebClient exchangeRateApiClient) {
        this.exchangeRateApiClient = exchangeRateApiClient;
    }

    @Override
    public Mono<Double> fetchRate(final ConversionRequest request) {
        return exchangeRateApiClient
                .get()
                .uri(url + request.getFrom())
                .retrieve()
                .bodyToMono(ExchangeRatesApiResponse.class)
                .map(response -> response.getRates().get(request.getTo()));
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
