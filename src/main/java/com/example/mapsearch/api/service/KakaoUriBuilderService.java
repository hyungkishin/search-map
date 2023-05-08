package com.example.mapsearch.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {

    private static final String KAKAO_SEARCH_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    private static final String KAKAO_LOCAL_SEARCH_CATEGORY_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    public URI buildUriByAddressSearch(String address) {

        URI uri = UriComponentsBuilder
                .fromHttpUrl(KAKAO_SEARCH_API_URL)
                .queryParam("query", address)
                .build()
                .encode()
                .toUri();

        log.info("builder: {}", address, uri);

        return uri;
    }

    public URI buildUrlByCategorySearch(double latitude, double longitude, double radius, String category) {

        double meterRadius = radius * 1000;

        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_CATEGORY_URL)
                .queryParam("category_group_code", category)
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .queryParam("radius", meterRadius)
                .queryParam("sort", "distance");

        URI uri = urlBuilder.build().encode().toUri();

        log.info("builder: {}", uri);
        return uri;
    }

}
