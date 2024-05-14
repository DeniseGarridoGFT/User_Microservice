package com.workshop.users.model;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.model.data.DataFromEntityTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ToEntityTests {

    @Test
    @DisplayName("Given a UserDto When fromEntity Then return a Entity")
    void UserToEntityTest() {
        UserDto userDto = DataFromEntityTest.USER_DTO_ID_2;
        assertThat(UserDto.toEntity(userDto)).isEqualTo(DataFromEntityTest.USER_ENTITY_ID_2);
    }
    @Test
    @DisplayName("Given a CountrDto When fromEntity Then return a Entity")
    void CountryToEntityTest() {
        CountryDto countryDto = DataFromEntityTest.COUNTRY_DTO_ESPANYA;
        assertThat(CountryDto.toEntity(countryDto)).isEqualTo(DataFromEntityTest.COUNTRY_ENTITY_ESPANYA);
    }
    @Test
    @DisplayName("Given a AddressDto When fromEntity Then return a Entity")
    void AddressToEntityTest() {
        AddressDto addressDto = DataFromEntityTest.ADDRESS_DTO_CALLE_VARAJAS;
        assertThat(AddressDto.toEntity(addressDto)).isEqualTo(DataFromEntityTest.ADDRESS_ENTITY_CALLE_VARAJAS);
    }
}
