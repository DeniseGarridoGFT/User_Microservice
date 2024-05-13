package com.workshop.users.model;

import com.workshop.users.api.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserStaticMethodsTest {
    @Nested
    @DisplayName("WhenCheckEmailExtern")
    class TestComprobeEmail {
        @Test
        @DisplayName("GivenValidEmail_WhenValidEmail_ThenReturnTrue")
        void testCheckFormatEmail_ValidEmail() {
            // Given
            String email = "test@example.com";

            // When
            boolean result = UserDto.checkFormatEmail(email);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("GivenInvalidEmail_WhenValidEmail_ThenReturnFalse")
        void testCheckFormatEmail_InvalidEmail() {
            // Given
            String email = "nonvalidemail_";

            // When
            boolean result = UserDto.checkFormatEmail(email);

            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("WhenCheckPasswordExtern")
    class TestComprobePassword {
        @Test
        @DisplayName("GivenValidPassword_WhenCheckPassword_ThenReturnTrue")
        void testCheckSecurityPassword_ValidPassword() {
            // Given
            String password ="SecurePassword1!";
            // When
            boolean result = UserDto.checkSecurityPassword(password);
            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("GivenInalidPassword_WhenCheckPassword_ThenReturnFalse")
        void testCheckSecurityPassword_InvalidPassword() {
            // Given
            String password ="weakpassword";
            // When
            boolean result = UserDto.checkSecurityPassword(password);
            // Then
            assertFalse(result);
        }
    }
    @Nested
    @DisplayName("WhenCheckPhoneExtern")
    class TestComprobePhone {
        @Test
        @DisplayName("GivenValidPhone_WhenCheckPhone_ThenReturnTrue")
        void testCheckPhoneFormat_ValidPhone() {
            // Given
            String phone = "123456789";
            // When
            boolean result = UserDto.checkPhoneFormat(phone);
            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("GivenInvalidPhone_WhenCheckPhone_ThenReturnFalse")
        void testCheckPhoneFormat_InvalidPhone() {
            // Given
            String phone = "1234567";
            // When
            boolean result = UserDto.checkPhoneFormat(phone);
            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("WhenCheckBirthDateExtern")
    class TestComprobeBirthDate {
        @Test
        @DisplayName("GivenValidBirthDate_WhenCheckBirthDate_ThenReturnTrue")
        void testCheckBirthDateFormat_ValidDate() {
            // Given
            String date = "2000/04/12";
            // When
            boolean result = UserDto.checkBirthDateFormat(date);

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("GivenInvalidBirthDate_WhenCheckBirthDate_ThenReturnFalse")
        void testCheckBirthDateFormat_InvalidDate() {
            // Given
            String ivalidDate = "12/04/2000";
            // When
            boolean result = UserDto.checkBirthDateFormat(ivalidDate);

            // Then
            assertFalse(result);
        }

        @Test
        @DisplayName("GivenOlder18Date_WhenCheckOlder18Date_ThenReturnTrue")
        void testIsOver18_Over18() {
            // Given
            String date = "2000/04/12";
            // When
            boolean result = UserDto.checkOver18(date);
            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("GivenNotOlder18Date_WhenCheckOlder18Date_ThenReturnFalse")
        void testIsOver18_Under18() {
            // Given
            String underageDate = "2023/04/12";
            // When
            boolean result = UserDto.checkOver18(underageDate);
            // Then
            assertFalse(result);
        }
    }
}
