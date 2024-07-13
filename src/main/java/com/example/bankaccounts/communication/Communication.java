package com.example.bankaccounts.communication;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class Communication {
    private final RestTemplate restTemplate;
    @Value("${key}")
    private String key;

    public String getKurs() {
        String data = restTemplate.getForObject("https://api.nbrb.by/exrates/rates/"+key, String.class);
        String target = "\"Cur_OfficialRate\":";
        int startIndex = data.indexOf(target) + target.length();
        int endIndex = data.indexOf("}", startIndex);
        String officialRateStr = data.substring(startIndex, endIndex);
        double officialRate = Double.parseDouble(officialRateStr);

        return officialRateStr;
    }
}