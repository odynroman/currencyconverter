package com.currencyconverter.model.dto;


import lombok.*;

@Getter
public class ConversionResponse extends ConversionData {
    private Double converted;

    @Builder
    public ConversionResponse(String from,String to, Double amount, Double converted) {
        super(from, to, amount);
        this.converted = converted;
    }
}
