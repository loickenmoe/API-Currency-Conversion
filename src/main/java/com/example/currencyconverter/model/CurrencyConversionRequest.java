package com.example.currencyconverter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionRequest {
    private String fromCurrency;
    private  String toCurrency;
    private double amount;
}
