package com.currencyconverter.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConversionRequest extends ConversionData {

    public ConversionRequest(String from, String to, Double amount) {
        super(from, to, amount);
    }
}
