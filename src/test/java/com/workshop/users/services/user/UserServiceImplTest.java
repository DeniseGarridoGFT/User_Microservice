package com.workshop.users.services.user;

import com.workshop.users.api.dto.UserDto;
import com.workshop.users.model.UserEntity;
import com.workshop.users.repositories.UserDAORepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
            Mockito.verify(userDAORepository).findById(Mockito.anyLong());
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
            Mockito.verify(userDAORepository).findById(Mockito.anyLong());
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


}