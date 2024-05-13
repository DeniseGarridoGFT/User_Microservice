package com.workshop.users.model;

import com.workshop.users.api.dto.CountryDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CountryTest {

    private CountryDto country;
    @BeforeEach
    void setUp() {
        country = CountryDto.builder()
                .name("España")
                .tax(21.0F)
                .prefix("+34")
                .timeZone("Madrid")
                .build();
    }




}
