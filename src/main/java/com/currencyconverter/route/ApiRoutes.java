package com.currencyconverter.route;


import com.currencyconverter.handler.ExchangeRatesHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@AllArgsConstructor
public class ApiRoutes {
    private final ExchangeRatesHandler exchangeRatesHandler;

    @Bean
    RouterFunction appRoutes() {
        return nest(
                path("/currency"),
                nest(accept(MediaType.APPLICATION_JSON),
                        route(POST("/convert"), exchangeRatesHandler::convert))
        );
    }
}
