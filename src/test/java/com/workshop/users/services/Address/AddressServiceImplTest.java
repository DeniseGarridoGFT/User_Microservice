package com.workshop.users.services.Address;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.exceptions.AddressNotFoundException;
import com.workshop.users.model.AddressEntity;
import com.workshop.users.repositories.AddressDAORepository;
import com.workshop.users.services.address.AddressService;
import com.workshop.users.services.address.AddressServiceImpl;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddressServiceImplTest {

    private AddressDAORepository addressDAORepository;
    private AddressService addressService;

    private AddressDto addressDto;
    private AddressEntity addressEntityWithId;

    @BeforeEach
    void setUp() {
        addressDAORepository = mock(AddressDAORepository.class);
        addressService = new AddressServiceImpl(addressDAORepository);
        addressDto = AddressDto.builder()
                .id(null)
                .door("1A")
                .number(12)
                .cityName("Madrid")
                .zipCode("36458")
                .street("C/ Las Ramblas")
                .build();
         addressEntityWithId = new AddressEntity();
         addressEntityWithId.setId(1L);
         addressEntityWithId.setDoor("1A");
         addressEntityWithId.setNumber(12);
         addressEntityWithId.setCityName("Madrid");
         addressEntityWithId.setZipCode("36458");
         addressEntityWithId.setStreet("C/ Las Ramblas");
    }

    @Test
    @DisplayName("When try to add Address then return the correct address")
    void addAddress() throws ParseException {
        when(addressDAORepository.save(any(AddressEntity.class))).thenReturn(addressEntityWithId);
        assertThat(addressService.addAddress(addressDto)).isEqualTo(AddressEntity.fromEntity(addressEntityWithId));
    }

    @Nested
    @DisplayName("When try to get Address")
    class GetAddress{
        @Test
        @DisplayName("Given a valid Id then return a user")
        void getAddress() {
            when(addressDAORepository.findById(anyLong())).thenReturn(Optional.of(addressEntityWithId));
            assertThat(addressService.getAddressById(1L)).isEqualTo(AddressEntity.fromEntity(addressEntityWithId));
        }
        @Test
        @DisplayName("Given a null Id then throw a error")
        void getAddressErrorIdNull() {
            RuntimeException runtimeException = null;

            try {
                addressService.getAddressById(null);
            }catch (Exception exception){
                runtimeException = (RuntimeException) exception;
            }
            assertThat(runtimeException.getMessage()).isEqualTo("Request not valid");
        }

        @Test
        @DisplayName("Given a non valid Id then throw a error")
        void getAddressErrorNotExistUserl() {
            Exception exceptionThrowIt = new Exception();
            when(addressDAORepository.findById(anyLong())).thenReturn(Optional.empty());
            try {
                addressService.getAddressById(1L);
            }catch (Exception exception){
                exceptionThrowIt = exception;
            }
            assertThat(exceptionThrowIt).isInstanceOf(RuntimeException.class);
        }
    }

    @Nested
    @DisplayName("When try to updated Address")
    class updateAddress{
        @Test
        @DisplayName("Given a address to change then return the address updated")
        void updateAddress() throws ParseException, AddressNotFoundException {
            addressDto.setId(1L);
            when(addressDAORepository.findById(anyLong())).thenReturn(Optional.of(addressEntityWithId));
            when(addressDAORepository.save(any(AddressEntity.class))).thenReturn(addressEntityWithId);
            assertThat(addressService.updateAddress(addressDto.getId(),addressDto)).isEqualTo(addressDto);
        }
    }
}
