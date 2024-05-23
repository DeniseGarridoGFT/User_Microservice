package com.workshop.users.model;

import com.workshop.users.api.dto.CountryDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CountryTest {

    CountryDto country;
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
