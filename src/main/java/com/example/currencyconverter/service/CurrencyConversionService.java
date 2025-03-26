package com.example.currencyconverter.service;

import com.example.currencyconverter.model.CurrencyConversionRequest;
import com.example.currencyconverter.model.CurrencyConversionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyConversionService {
    private final WebClient webClient;

    @Value("${exchangerate.api.base-url}")
    private String apiBaseUrl;

    @Value("${exchangerate.api.key}")
    private String apiKey;

    public CurrencyConversionResponse convertCurrency(CurrencyConversionRequest request) {
        String url = apiBaseUrl + apiKey + "/latest/" + request.getFromCurrency();

        Map<String, Object> response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null || !response.containsKey("conversion_rates")) {
            throw new RuntimeException("Impossible d'obtenir les taux de change.");
        }

        Map<String, Double> rates = (Map<String, Double>) response.get("conversion_rates");
        Double exchangeRate = rates.get(request.getToCurrency());

        if (exchangeRate == null) {
            throw new RuntimeException("Devise cible invalide.");
        }

        double convertedAmount = request.getAmount() * exchangeRate;

        return new CurrencyConversionResponse(
                request.getFromCurrency(),
                request.getToCurrency(),
                request.getAmount(),
                convertedAmount,
                exchangeRate
        );
    }
}
