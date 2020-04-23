package com.currencyconverter.model.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConversionData {
    @Size(min = 3, max = 3, message = "The length of the 'from' parameter must be equal 3.")
    @NonNull
    private String from;
    @Size(min = 3, max = 3, message = "The length of the 'to' parameter must be equal 3.")
    @NonNull
    private String to;
    @NonNull
    private Double amount;
}
