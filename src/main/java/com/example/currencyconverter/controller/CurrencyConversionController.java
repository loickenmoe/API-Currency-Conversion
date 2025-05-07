package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.CurrencyConversionRequest;
import com.example.currencyconverter.model.CurrencyConversionResponse;
import com.example.currencyconverter.service.CurrencyConversionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/conversion")
public class CurrencyConversionController {

    private final CurrencyConversionService currencyConversionService;

    public CurrencyConversionController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @Operation(summary = "Convertir une devise en une autre")
    @PostMapping("/convert")
    public ResponseEntity<?> convertCurrency(@Valid @RequestBody CurrencyConversionRequest request) throws Exception {
        // Validation supplémentaire pour le format des devises
        if (!isValidCurrencyCode(request.getSourceCurrency()) || !isValidCurrencyCode(request.getTargetCurrency())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Les devises doivent être au format ISO 3 lettres (ex: EUR)");
            return ResponseEntity.badRequest().body(error);
        }

        CurrencyConversionResponse response = currencyConversionService.convertCurrency(request);
        return ResponseEntity.ok(response);
    }

    private boolean isValidCurrencyCode(String currencyCode) {
        return currencyCode != null
                && currencyCode.length() == 3
                && currencyCode.matches("[A-Z]{3}");
    }
}