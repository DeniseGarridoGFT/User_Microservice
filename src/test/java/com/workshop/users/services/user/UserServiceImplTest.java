package com.workshop.users.services.user;

import com.workshop.users.api.controller.Data.DataToUserControllerTesting;
import com.workshop.users.api.dto.Login;
import com.workshop.users.api.dto.UserDto;
import com.workshop.users.model.AddressEntity;
import com.workshop.users.model.UserEntity;
import com.workshop.users.repositories.UserDAORepository;
import org.assertj.core.api.Assertions;
import org.h2.engine.User;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.*;

class UserServiceImplTest {

    private UserDAORepository userDAORepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDAORepository = Mockito.mock(UserDAORepository.class);
        userService = new UserServiceImpl(userDAORepository);
    }

    @AfterEach
    void tearDown() {
        DataToMockInUserServiceImplTest.USER_1.setName("Manuel");
        DataToMockInUserServiceImplTest.USER_1.setId(2L);
        DataToMockInUserServiceImplTest.USER_1.setEmail("manuel@example.com");
        DataToMockInUserServiceImplTest.USER_1.setName("Manuel");
        DataToMockInUserServiceImplTest.USER_1.setPassword("2B8sda2?_");
        DataToMockInUserServiceImplTest.USER_1.setLastName("Salamanca");
        DataToMockInUserServiceImplTest.USER_1.setPhone("839234012");
        DataToMockInUserServiceImplTest.USER_1.setBirthDate(new Date("2000/14/01"));
        DataToMockInUserServiceImplTest.USER_1.setFidelityPoints(50);
    }

    @Nested
    @DisplayName("when get user by id")
    class GetUserById {

        @Test
        @DisplayName("givenNonExistingId_whenGetUserById_thenThrowsRunTimeException ")
        void getUserByIdAddingNotExistingUser() {
            //Given
            Mockito.when(userDAORepository.findById(3L))
                    .thenThrow(new RuntimeException("User not found"));
            //When and Then
            assertThrows(RuntimeException.class, () -> userService.getUserById(3L));
            verify(userDAORepository).findById(Mockito.anyLong());
        }
        @Test
        @DisplayName("givenNull_whenGetUserById_thenThrowsRunTimeException")
        void getUserByIdAddingNull() {

            //When and Then
            assertThrows(RuntimeException.class, () -> userService.getUserById(null));
        }
        @Test
        @DisplayName("givenId_whenGetUserById_thenReturnTheAssociatedUser")
        void getUserById() {
            //Given
            Mockito.when(userDAORepository.findById(2L))
                    .thenReturn(Optional.of(DataToMockInUserServiceImplTest.USER_1));
            //When
            UserDto user_id_2 = userService.getUserById(2L);

            //Then
            assertEquals("Manuel", user_id_2.getName());
            assertEquals(3L, user_id_2.getAddress().getId());
            assertEquals(1L, user_id_2.getCountry().getId());
            verify(userDAORepository).findById(Mockito.anyLong());
        }




    @Test
    @DisplayName("Given an user to update when update user then return the user dto updated")
    void testUpdateUser() {
        UserEntity userEntity = DataToMockInUserServiceImplTest.USER_1;

        UserDto userDtoUpdated = UserDto.builder()
                .name("Manuel updated")
                .lastName("Salamanca updated")
                .password("2B8sda2?_")
                .phone("963258741")
                .email("manuelupdated@example.com")
                .birthDate("2000/01/14")
                .fidelityPoints(60)
                .country(DataToUserControllerTesting.COUNTRY_ESPANYA)
                .address(DataToUserControllerTesting.ADDRESS_CALLE_VARAJAS)
                .build();

        Mockito.when(userDAORepository.findById(2L)).thenReturn(Optional.of(userEntity));
        Mockito.when(userDAORepository.save(any(UserEntity.class))).thenReturn(UserDto.toEntity(userDtoUpdated));
        UserDto updatedUser = userService.updateUser(2L, userDtoUpdated);

        Assertions.assertThat(updatedUser.getName()).isEqualTo("Manuel updated");
        Assertions.assertThat(updatedUser.getLastName()).isEqualTo("Salamanca updated");
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo("manuelupdated@example.com");
        Assertions.assertThat(updatedUser.getBirthDate()).isEqualTo(userDtoUpdated.getBirthDate());
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("2B8sda2?_");
        Assertions.assertThat(updatedUser.getFidelityPoints()).isEqualTo(60);
        Assertions.assertThat(updatedUser.getPhone()).isEqualTo("963258741");
        Assertions.assertThat(updatedUser.getAddress()).isNotNull();

        verify(userDAORepository).findById(2L);
        verify(userDAORepository).save(any(UserEntity.class));
    }
    }


    @Nested
    @DisplayName("When get user by email")
    class GetUserByEmail{
        @Test
        @DisplayName("Given existed email return good user")
        void getUserByExistingEmail() {
            UserDto userExpected = UserEntity.fromEntity(DataToMockInUserServiceImplTest.USER_1);
            //Given
            when(userDAORepository.findByEmail("manuel@example.com")).thenReturn(Optional.of(DataToMockInUserServiceImplTest.USER_1));
            //When
            UserDto userDto = userService.getUserByEmail("manuel@example.com");
            //Then
            assertThat(userDto).isEqualTo(userExpected);

        }

        @Test
        @DisplayName("Given a non-existent email return null")
        void getUserByNonExistingEmail() {
            //Given
            when(userDAORepository.findByEmail("paquito@perez.com")).thenReturn(null);
            //When and Then
            assertThatThrownBy(()->{
                userService.getUserByEmail("paquito@perez.com");
            }).isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("Given a null email return null")
        void getUserByNullEmail() {
            //Given null
            //When and Then
            assertThatThrownBy(()->{
                userService.getUserByEmail(null);
            }).isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Request not valid");
        }
    }

    @Test
    @DisplayName("Given an userdto when add user then save the user")
    void addUser() throws ParseException {
        UserDto userDtoToSave = UserDto.builder()
                .name("Manuel updated")
                .lastName("Salamanca updated")
                .password("2B8sda2?_")
                .phone("963258741")
                .email("manuelupdated@example.com")
                .birthDate("2000/01/14")
                .fidelityPoints(60)
                .country(DataToUserControllerTesting.COUNTRY_ESPANYA)
                .address(DataToUserControllerTesting.ADDRESS_CALLE_VARAJAS)
                .build();
        UserDto userDtoAux = userDtoToSave;
        userDtoAux.setId(1L);
        when(userDAORepository.save(any(UserEntity.class))).thenReturn(UserDto.toEntity(userDtoAux));
        UserDto userSaved = userService.addUser(userDtoToSave);

        Assertions.assertThat(userSaved.getName()).isEqualTo("Manuel updated");
        Assertions.assertThat(userSaved.getLastName()).isEqualTo("Salamanca updated");
        Assertions.assertThat(userSaved.getEmail()).isEqualTo("manuelupdated@example.com");
        Assertions.assertThat(userSaved.getBirthDate()).isEqualTo(userSaved.getBirthDate());
        Assertions.assertThat(Login.BCRYPT.matches(userSaved.getPassword(),userDtoToSave.getPassword())).isTrue();
        Assertions.assertThat(userSaved.getFidelityPoints()).isEqualTo(60);
        Assertions.assertThat(userSaved.getPhone()).isEqualTo("963258741");
        Assertions.assertThat(userSaved.getAddress()).isNotNull();
    }

}