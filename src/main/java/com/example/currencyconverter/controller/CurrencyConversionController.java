package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.CurrencyConversionRequest;
import com.example.currencyconverter.model.CurrencyConversionResponse;
import com.example.currencyconverter.service.CurrencyConversionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversion")
@RequiredArgsConstructor
public class CurrencyConversionController {
    private final CurrencyConversionService conversionService;

    @Operation(summary = "Convertir une devise en une autre")
    @PostMapping
    public CurrencyConversionResponse convertCurrency(@RequestBody CurrencyConversionRequest request) {
        return conversionService.convertCurrency(request);
    }
}
