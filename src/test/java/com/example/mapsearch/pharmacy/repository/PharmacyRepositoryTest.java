package com.example.mapsearch.pharmacy.repository;

import com.example.mapsearch.AbstractIntegrationContainerBaseTest;
import com.example.mapsearch.pharmacy.entity.Pharmacy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Test
    void testSavePharmacy() {
        // Given
        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyName("Test Pharmacy")
                .pharmacyAddress("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();

        // When
        pharmacyRepository.save(pharmacy);

        // Then
        Pharmacy savedPharmacy = pharmacyRepository.findById(pharmacy.getId()).orElse(null);
        assertThat(savedPharmacy.getPharmacyName()).isEqualTo("Test Pharmacy");
    }

}
