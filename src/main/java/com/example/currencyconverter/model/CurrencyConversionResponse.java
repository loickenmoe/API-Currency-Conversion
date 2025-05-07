package com.example.currencyconverter.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionResponse {

    @NotBlank(message = "Source currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Format devise invalide (ex: EUR)")
    private String sourceCurrency;

    private String targetCurrency;
    private double sourceAmount;
    private double convertedAmount;
    private double exchangeRate;
    private String lastUpdateTime;
}

