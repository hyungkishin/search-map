package com.example.mapsearch.pharmacy.service;

import com.example.mapsearch.api.dto.Document;
import com.example.mapsearch.api.dto.KakaoApiResponse;
import com.example.mapsearch.api.service.KakaoAddressSearchService;
import com.example.mapsearch.direction.entity.Direction;
import com.example.mapsearch.direction.response.DirectionRes;
import com.example.mapsearch.direction.service.DirectionService;
import com.example.mapsearch.pharmacy.dto.PharmacyDto;
import com.example.mapsearch.pharmacy.entity.Pharmacy;
import com.example.mapsearch.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    private final KakaoAddressSearchService kakaoAddressSearchService;

    private final DirectionService directionService;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    private static final String DIRECTION_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    /**
     * 고객의 주소를 받아서 공공기관 약국 데이터를 이용하여 가장 가까운 약국을 추천해주는 서비스
     * @param address
     * @return List<DirectionRes>
     */
    public List<DirectionRes> recommendPharmacy(String address) {

        KakaoApiResponse kakaoApiResponse = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponse) || CollectionUtils.isEmpty(kakaoApiResponse.getDocumentList())) {
            log.error("kakao api response is null or document list is empty");
            return Collections.emptyList();
        }

        final Document document = kakaoApiResponse
                .getDocumentList()
                .get(0);

        // 공공기관 약국 데이터 및 거리계산 알고리즘 이용
        // List<Direction> directionList = directionService.buildDirectionList(document);

        // kakao 카테고리를 이용한 장소 검색 api 허용
        final List<Direction> directions = directionService.buildDirectionListByCategoryApi(document);

        return directionService
                .saveAll(directions)
                .stream()
                .map(DirectionRes::of)
                .collect(Collectors.toList());
    }

    public List<PharmacyDto> searchPharmacyList() {
        final List<Pharmacy> pharmacyList = pharmacyRepository.findAll();

        return pharmacyList.stream()
                .map(PharmacyDto::of)
                .collect(Collectors.toList());
    }
}
