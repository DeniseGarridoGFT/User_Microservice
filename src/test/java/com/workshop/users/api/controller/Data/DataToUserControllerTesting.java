package com.workshop.users.api.controller.Data;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.api.dto.UserDto;

public class DataToUserControllerTesting {


    public final static AddressDto ADDRESS_CALLE_VARAJAS = AddressDto.builder()
                    .id(3L)
                    .cityName("Madrid")
                    .street("C/VarajasNavaja")
                    .zipCode("43567")
                    .number(3)
                    .door("1A")
                    .build();

    public final static CountryDto COUNTRY_ESPANYA = CountryDto.builder()
            .id(1L)
                .name("España")
                .tax(21F)
                .prefix("+34")
                .timeZone("Europe/Madrid")
                .build();

    public final static UserDto USER_ID_2 =  UserDto.builder()
            .id(2L)
            .name("Denise")
            .lastName("Garrido")
            .email("denise@gmail.com")
            .address(ADDRESS_CALLE_VARAJAS)
            .birthDate("2004/14/04")
            .password("passwordAS123")
            .phone("12346789")
            .fidelityPoints(100)
            .country(COUNTRY_ESPANYA)
            .build();

    public final static UserDto USER_ID_2_MODIFIED =  UserDto.builder()
            .id(2L)
            .name("Denise")
            .lastName("Garrido")
            .email("denisegarridogmail.com")
            .address(ADDRESS_CALLE_VARAJAS)
            .birthDate("2004/12/04")
            .password("passwordAS123")
            .phone("12346789")
            .fidelityPoints(100)
            .country(COUNTRY_ESPANYA)
            .build();

}
