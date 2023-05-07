package com.example.mapsearch.direction.service;

import com.example.mapsearch.api.dto.Document;
import com.example.mapsearch.direction.entity.Direction;
import com.example.mapsearch.direction.repository.DirectionRepository;
import com.example.mapsearch.pharmacy.dto.PharmacyDto;
import com.example.mapsearch.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectionService {

    // 최대 검색 갯수
    private static final int MAX_SEARCH_COUNT = 3;

    // 반경 10km 이내
    private static final double MAX_KM = 10.0;

    private final PharmacySearchService pharmacySearchService;

    private final DirectionRepository directionRepository;

    @Transactional
    public List<Direction> saveAll(List<Direction> directionList) {
        if (CollectionUtils.isEmpty(directionList)) return Collections.EMPTY_LIST;
        return directionRepository.saveAll(directionList);
    }

    public List<Direction> buildDirectionList(Document documentDto) {
        if (documentDto == null) return Collections.EMPTY_LIST;

        final List<PharmacyDto> pharmacies = pharmacySearchService.searchPharmacyList();

        return pharmacies.stream()
                .map(pharmacyDto -> Direction.builder()
                        .inputAddress(documentDto.getAddressName())
                        .inputLatitude(documentDto.getLatitude())
                        .inputLongitude(documentDto.getLongitude())
                        .targetPharmacyName(pharmacyDto.getPharmacyName())
                        .targetAddress(pharmacyDto.getPharmacyAddress())
                        .targetLatitude(pharmacyDto.getLatitude())
                        .targetLongitude(pharmacyDto.getLongitude())
                        .distance(
                                calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(), pharmacyDto.getLatitude(), pharmacyDto.getLongitude())
                        )
                        .build()) // 거리 계산
                .filter(direction -> direction.getDistance() <= MAX_KM) // 반경 10km 이내
                .sorted(Comparator.comparing(Direction::getDistance)) // 거리순으로 정렬
                .limit(MAX_SEARCH_COUNT) // 최대 검색 갯수
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1); // 고객의 주소
        lon1 = Math.toRadians(lon1); // 고객의 주소
        lat2 = Math.toRadians(lat2); // 약국의 주소
        lon2 = Math.toRadians(lon2); // 약국의 주소

        double earthRadius = 6371.01; // 지구의 반지름 (단위: km)
        double distance = earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2)); // 거리 계산 공식
        return distance;
    }
}
