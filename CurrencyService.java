package com.example.currency.service;

import com.example.currency.model.ExchangeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CurrencyService {

    @Value("${EXCHANGE_API}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public BigDecimal convertCurrency(String from, String to, BigDecimal amount) {
        String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s", apiKey, from, to);
        ExchangeResponse response = restTemplate.getForObject(url, ExchangeResponse.class);
        if (response == null || response.getConversionRate() == null) {
            throw new RuntimeException("Erro ao obter taxa de câmbio");
        }
        return amount.multiply(response.getConversionRate());
    }
}
