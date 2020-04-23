package com.currencyconverter.service.impl;

import com.currencyconverter.exception.ApplicationException;
import com.currencyconverter.model.dto.ConversionRequest;
import com.currencyconverter.model.dto.ExchangeRatesApiResponse;
import com.currencyconverter.service.ExchangeRatesProvider;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
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
                .exchange()
                .flatMap(this::handleRatesRequest)
                .map(response -> response.getRates().get(request.getTo()));
    }

    private Mono<? extends ExchangeRatesApiResponse> handleRatesRequest(ClientResponse clientResponse) {
        if (!clientResponse.statusCode().is2xxSuccessful()) {
            return Mono.error(new ApplicationException("No possibility to fetch exchange rates."));
        }
        return clientResponse.bodyToMono(ExchangeRatesApiResponse.class);
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
