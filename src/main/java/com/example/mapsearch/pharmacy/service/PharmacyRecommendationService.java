package com.example.mapsearch.pharmacy.service;

import com.example.mapsearch.api.dto.Document;
import com.example.mapsearch.api.dto.KakaoApiResponse;
import com.example.mapsearch.api.service.KakaoAddressSearchService;
import com.example.mapsearch.direction.entity.Direction;
import com.example.mapsearch.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;

    private final DirectionService directionService;

    public void recommendPharmacy(String address) {

        KakaoApiResponse kakaoApiResponse = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponse) || CollectionUtils.isEmpty(kakaoApiResponse.getDocumentList())) {
            throw new RuntimeException("kakao api response is null"); // TODO: custom exception
        }

        final Document document = kakaoApiResponse.getDocumentList().get(0);

        final List<Direction> directions = directionService.buildDirectionListByCategoryApi(document);

        directionService.saveAll(directions);

    }

}
