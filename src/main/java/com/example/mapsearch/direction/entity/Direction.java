package com.example.mapsearch.direction.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Direction {

    @Id @GeneratedValue
    private Long id;

    // 고객의 주소 정보
    private String inputAddress;

    private double inputLatitude;

    private double inputLongitude;

    // 약국의 주소 정보
    private String targetPharmacyName;

    private String targetAddress;

    private double targetLatitude;

    private double targetLongitude;

    // 고객 주소 와 약국 주소 사이의 거리
    private double distance;
}