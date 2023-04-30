package com.example.mapsearch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {

    private static final String KAKAO_SEARCH_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public URI buildUriByAddressSearch(String address) {

        URI uri = UriComponentsBuilder
                .fromHttpUrl(KAKAO_SEARCH_API_URL)
                .queryParam("query", address)
                .build().encode().toUri();

        log.info("builder: {}", address, uri);

        return uri;
    }

}
