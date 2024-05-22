package com.workshop.users.model;


import static org.junit.jupiter.api.Assertions.*;

import com.workshop.users.api.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class UserTest {
    @Nested
    @DisplayName("WhenCheckEmail")
    class TestComprobeEmail {
        @Test
        @DisplayName("GivenValidEmail_WhenValidEmail_ThenReturnTrue")
        void testCheckFormatEmail_ValidEmail() {
            // Given
            UserDto userDto = UserDto.builder()
                    .email("test@example.com")
                    .build();

            // When
            boolean result = userDto.checkFormatEmail();

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("GivenInvalidEmail_WhenValidEmail_ThenReturnFalse")
        void testCheckFormatEmail_InvalidEmail() {
            // Given
            UserDto userDto = UserDto.builder()
                    .email("invalid_email")
                    .build();

            // When
            boolean result = userDto.checkFormatEmail();

            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("WhenCheckPassword")
    class TestComprobePassword {
        @Test
        @DisplayName("GivenValidPassword_WhenCheckPassword_ThenReturnTrue")
        void testCheckSecurityPassword_ValidPassword() {
            // Given
            UserDto userDto = UserDto.builder()
                    .password("SecurePassword1!")
                    .build();
            // When
            boolean result = userDto.checkSecurityPassword();
            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("GivenInalidPassword_WhenCheckPassword_ThenReturnFalse")
        void testCheckSecurityPassword_InvalidPassword() {
            // Given
            UserDto userDto = UserDto.builder()
                    .password("weakpassword")
                    .build();
            // When
            boolean result = userDto.checkSecurityPassword();
            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("WhenCheckPhone")
    class TestComprobePhone {
        @Test
        @DisplayName("GivenValidPhone_WhenCheckPhone_ThenReturnTrue")
        void testCheckPhoneFormat_ValidPhone() {
            // Given
            UserDto userDto = UserDto.builder()
                    .phone("123456789")
                    .build();
            // When
            boolean result = userDto.checkPhoneFormat();
            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("GivenInvalidPhone_WhenCheckPhone_ThenReturnFalse")
        void testCheckPhoneFormat_InvalidPhone() {
            // Given
            UserDto userDto = UserDto.builder()
                    .phone("1234567")
                    .build();
            // When
            boolean result = userDto.checkPhoneFormat();
            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("WhenCheckBirthDate")
    class TestComprobeBirthDate {
        @Test
        @DisplayName("GivenValidBirthDate_WhenCheckBirthDate_ThenReturnTrue")
        void testCheckBirthDateFormat_ValidDate() {
            // Given
            UserDto userDto = UserDto.builder()
                    .birthDate("2000/04/12")
                    .build();
            // When
            boolean result = userDto.checkBirthDateFormat();

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("GivenInvalidBirthDate_WhenCheckBirthDate_ThenReturnFalse")
        void testCheckBirthDateFormat_InvalidDate() {
            // Given
            UserDto userDto = UserDto.builder()
                    .birthDate("12/04/2000")
                    .build();
            // When
            boolean result = userDto.checkBirthDateFormat();

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("GivenOlder18Date_WhenCheckOlder18Date_ThenReturnTrue")
        void testIsOver18_Over18() {
            // Given
            UserDto userDto = UserDto.builder()
                    .birthDate("2000/04/12")
                    .build();
            // When
            boolean result = userDto.checkOver18();
            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("GivenNotOlder18Date_WhenCheckOlder18Date_ThenReturnFalse")
        void testIsOver18_Under18() {
            // Given
            UserDto userDto = UserDto.builder()
                    .birthDate("2023/04/12")
                    .build();
            // When
            boolean result = userDto.checkOver18();
            // Then
            assertFalse(result);
        }
    }


    @Nested
    @DisplayName("WhenSetBirthDate")
    class TestComprobeSetBirthDate {


        @Test
        @DisplayName("GivenValidBirthdate_WhenSetBirthDate_ThenSetTheBirthDate")
        void testSetValidBirthDate_ValidBirthDate() throws Exception {
            // Given
            UserDto userDto = UserDto.builder().build();

            // When
            userDto.setValidBirthDate("2000/04/12");

            // Then
            assertEquals("2000/04/12", userDto.getBirthDate());
        }


        @Test
        @DisplayName("GivenInvalidBirthdate_WhenSetBirthDate_ThenThrowExceptionInvalidFormat")
        void testSetValidBirthDate_InvalidBirthDate() {
            // Given
            UserDto userDto = UserDto.builder().build();

            // When
            Exception exception = assertThrows(Exception.class, () -> userDto.setValidBirthDate("56075"));

            // Then
            assertEquals("The format of the birthd date is not valid", exception.getMessage());
        }

        @Test
        @DisplayName("GivenNonOlderUnder18Date_WhenSetBirthDate_ThenThrowExceptionNotOlder18")
        void testSetOver18BirthDate_NotOlder18() {
            // Given
            UserDto userDto = UserDto.builder().build();

            // When
            Exception exception = assertThrows(Exception.class, () -> userDto.setOver18BirthDate("2023/04/12"));

            // Then
            assertEquals("You aren't 18", exception.getMessage());
        }


    }


    @Nested
    @DisplayName("WhenSetPhone")
    class TestComprobeSetPhone {
        @Test
        @DisplayName("GivenValidPhone_WhenSetPhone_ThenSetThePhone")
        void testSetValidPhone_ValidPhone() throws Exception {
            // Given
            UserDto userDto = UserDto.builder().build();

            // When
            userDto.setValidPhone("123456789");

            // Then
            assertEquals("123456789", userDto.getPhone());
        }

        @Test
        @DisplayName("GivenInvalidPhone_WhenSetPhone_ThenThrowAnException")
        void testSetValidPhone_InvalidPhone() {
            // Given
            UserDto userDto = UserDto.builder().build();

            // When
            Exception exception = assertThrows(Exception.class, () -> userDto.setValidPhone("1234567"));

            // Then
            assertEquals("The email is not valid", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("WhenSetPassword")
    class TestComprobeSetPassword {
        @Test
        @DisplayName("GivenInsecurePassword_WhenSetPassword_ThenThrowAnException")
        void testSetSecurePassword_InvalidPassword() {
            // Given
            UserDto userDto = UserDto.builder().build();
            // When
            Exception exception = assertThrows(Exception.class, () -> userDto.setSecurePassword("dasdadsad"));
            // Then
            assertEquals("The password is not secure", exception.getMessage());
        }

        @Test
        @DisplayName("GivenSecurePassword_WhenSetPassword_ThenSetThePassword")
        void testSetSecurePassword_ValidPassword() throws Exception {
            // Given
            UserDto userDto = UserDto.builder().build();

            // When
            userDto.setSecurePassword("SecurePassword1!");

            // Then
            assertEquals("SecurePassword1!", userDto.getPassword());
        }

    }


    @Nested
    @DisplayName("WhenSetEmail")
    class TestComprobeSetEmail {
        @Test
        @DisplayName("GivenInvalidEmail_WhenSetEmail_ThenThrowAnException")
        void testSetValidEmail_InvalidEmail() {
            // Given
            UserDto userDto = UserDto.builder().build();

            // When
            Exception exception = assertThrows(Exception.class, () -> userDto.setValidEmail("invalid_email"));

            // Then
            assertEquals("The email is not valid", exception.getMessage());
        }

        @Test
        @DisplayName("GivenValidEmail_WhenSetEmail_ThenSetTheEmail")
        void testSetValidEmail_ValidEmail() throws Exception {
            // Given
            UserDto userDto = UserDto.builder().build();

            // When
            userDto.setValidEmail("test@example.com");

            // Then
            assertEquals("test@example.com", userDto.getEmail());
        }
    }

    @Nested
    @DisplayName("Set save fidelity points")
    class FidelityPoints {
        @Test
        @DisplayName("Given a valid fidelity points then update them")
        void setSaveFidelityPointsTest() {
            Integer result = UserDto.setSaveFidelityPoints(2, 2);
            assertThat(result).isNotNull().isEqualTo(4);
        }

        @Test
        @DisplayName("Given a non valid fidelity points then return0")
        void setSaveFidelityPointsTestReturnZero() {
            Integer result = UserDto.setSaveFidelityPoints(2, -50);
            assertThat(result).isNotNull().isZero();
        }
    }


}

