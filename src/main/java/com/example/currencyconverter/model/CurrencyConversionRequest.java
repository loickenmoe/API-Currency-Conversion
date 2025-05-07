package com.example.currencyconverter.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionRequest {

    @NotBlank(message = "Source currency is required")
    private String sourceCurrency;

    @NotBlank(message = "Target currency is required")
    private  String targetCurrency;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private double amount;
}
