package com.example.mapsearch.direction.response;


import com.example.mapsearch.direction.entity.Direction;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DirectionRes {

    private String pharmacyName; // 약국명

    private String pharmacyAddress; // 약국 주소

    private String directionUrl; // 길안내 url

    private String roadViewUrl; // 로드뷰 url

    private String distance; // 고객 주소와 약국 주소와 의 거리

    public static DirectionRes of(Direction directions) {
        return DirectionRes.builder()
                .pharmacyName(directions.getTargetPharmacyName())
                .pharmacyAddress(directions.getTargetAddress())
                .directionUrl("url")
                .roadViewUrl("url")
                .distance(String.format("%.2f km", directions.getDistance()))
                .build();
    }
}
