package com.example.currencyconverter.service;

import com.example.currencyconverter.exception.ExchangeApiException;
import com.example.currencyconverter.model.CurrencyConversionRequest;
import com.example.currencyconverter.model.CurrencyConversionResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class CurrencyConversionService {


    @Value("${exchangerate.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${exchangerate.api.base-url}")
    private String apiBaseUrl;



    public CurrencyConversionService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public CurrencyConversionResponse convertCurrency(CurrencyConversionRequest request) throws Exception {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiBaseUrl)
                    .path(apiKey)
                    .path("/latest/")
                    .path(request.getSourceCurrency())
                    .toUriString();

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());

            if (!root.path("result").asText().equals("success")) {
                throw new ExchangeApiException("Failed to get exchange rates");
            }

            JsonNode rates = root.path("conversion_rates");
            if (!rates.has(request.getTargetCurrency())) {
                throw new ExchangeApiException("Invalid target currency: " + request.getTargetCurrency());
            }

            double rate = rates.path(request.getTargetCurrency()).asDouble();
            double convertedAmount = request.getAmount() * rate;

            CurrencyConversionResponse currencyConversionResponse = new CurrencyConversionResponse();
            currencyConversionResponse.setSourceCurrency(request.getSourceCurrency());
            currencyConversionResponse.setTargetCurrency(request.getTargetCurrency());
            currencyConversionResponse.setSourceAmount(request.getAmount());
            currencyConversionResponse.setConvertedAmount(convertedAmount);
            currencyConversionResponse.setExchangeRate(rate);
            currencyConversionResponse.setLastUpdateTime(root.path("time_last_update_utc").asText());

            return currencyConversionResponse;
        } catch (HttpClientErrorException.NotFound e) {
            JsonNode errorResponse;
            try {
                errorResponse = objectMapper.readTree(e.getResponseBodyAsString());
                String errorType = errorResponse.path("error-type").asText();
                if ("unsupported-code".equals(errorType)) {
                    throw new ExchangeApiException("Invalid source currency: " + request.getSourceCurrency());
                }
            } catch (Exception ex) {
                throw new Exception(ex);
            }
            throw new ExchangeApiException("Invalid source currency: " + request.getSourceCurrency());
        } catch (Exception e) {
            throw new ExchangeApiException("Error while converting currency: " + e.getMessage());
        }
    }
}
