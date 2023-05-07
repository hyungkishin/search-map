package com.example.mapsearch.pharmacy.service;

import com.example.mapsearch.api.dto.KakaoApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final KakaoUriBuilderService kakaoUriBuilderService;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey = "88b2073ae586a8d628c80c6bc5d7f022";


    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 2, // 최대 시도할 횟수
            backoff = @Backoff(delay = 2000) // 2초 간격으로 백오프
    )
    public KakaoApiResponse requestAddressSearch(String address) {

        URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        // kakao api 호출
        return restTemplate
                .exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponse.class)
                .getBody();
    }

    @Recover
    public KakaoApiResponse recover(RuntimeException e, String address) {
        log.error("All the retries failed. address: {}, error: {}", address, e.getMessage());
        return null;
    }

}
