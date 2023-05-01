package com.example.mapsearch.service

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class KakaoUriBuilderServiceTest extends Specification {


    private KakaoUriBuilderService kakaoUriBuilderService;

    def setup() {
        kakaoUriBuilderService = new KakaoUriBuilderService()
    }

    def "buildUriByaddressSearch - 한글 파라미터의 경우 정상적으로 인코딩"() {

        given:
        String address = "서울 성북구"
        def charset = StandardCharsets.UTF_8

        when:
        def uri = kakaoUriBuilderService.buildUriByAddressSearch(address) // groovy 는 동적 타입 언어라 자동추론됨
        def decodedResult = URLDecoder.decode(uri.toString(), charset)

        then:
        URLDecoder.decode(uri.toString(), charset) == decodedResult // 한글 파라미터의 경우 정상적으로 인코딩
    }

}
