package com.workshop.users.api.dto;

import com.workshop.users.model.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Data
@Builder
public class UserDto implements Serializable {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private Integer fidelityPoints;
    @NotNull
    private String birthDate;
    @NotNull
    private String phone;
    @NotNull
    private AddressDto address;
    @NotNull
    private CountryDto country;

    public static UserEntity toEntity(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setFidelityPoints(dto.getFidelityPoints());
        entity.setBirthDate(new Date(dto.getBirthDate()));
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress()!=null?AddressDto.toEntity(dto.getAddress()):null);
        entity.setCountry(CountryDto.toEntity(dto.getCountry()));


        return entity;
    }

    public boolean checkFormatEmail() {
        String emailFormat = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return getEmail().matches(emailFormat);
    }

    public static boolean checkFormatEmail(String email) {
        String emailFormat = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(emailFormat);
    }

    public boolean checkSecurityPassword() {
        String passwordSecureFormat =  "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return getPassword().matches(passwordSecureFormat);
    }
    public static boolean checkSecurityPassword(String password) {
        String passwordFormat =  "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return password.matches(passwordFormat);
    }

    public boolean checkPhoneFormat() {
        String phoneFormat = "^\\d{9}$";
        return getPhone().matches(phoneFormat);
    }

    public static boolean checkPhoneFormat(String phone) {
        String phoneFormat = "^\\d{9}$";
        return phone.matches(phoneFormat);
    }



    public boolean checkBirthDateFormat(){
        String dateFormat = "^\\d{4}/(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])$";
        return getBirthDate().matches(dateFormat);
    }
    public static boolean checkBirthDateFormat(String birthDate){
        String dateFormat = "^\\d{4}/(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])$";
        return birthDate.matches(dateFormat);
    }


    public boolean checkOver18() {
        String[] array = getBirthDate().split("/");
        LocalDate now = LocalDate.now();
        return  Period.between(LocalDate.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]),Integer.parseInt(array[2])), now).getYears() >= 18;
    }

    public static boolean checkOver18(String date) {
        String[] array = date.split("/");
        LocalDate now = LocalDate.now();
        return  Period.between(LocalDate.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]),Integer.parseInt(array[2])), now).getYears() >= 18;
    }


    public void setValidBirthDate(String birthDate) throws Exception{
        if (UserDto.checkBirthDateFormat(birthDate))
            setOver18BirthDate(birthDate);
        else
            throw new Exception("The format of the birthd date is not valid");
    }
    public void setOver18BirthDate(String birthDate) throws Exception{
        if (UserDto.checkOver18(birthDate))
            setBirthDate(birthDate);
        else
            throw new Exception("You aren't 18");
    }

    public void setSecurePassword(String password) throws Exception{
        if (UserDto.checkSecurityPassword(password))
            setPassword(password);
        else
            throw new Exception("The password is not secure");
    }
    public void setValidEmail(String email) throws Exception{
        if (UserDto.checkFormatEmail(email))
            setEmail(email);
        else
            throw new Exception("The email is not valid");
    }
    public void setValidPhone(String phone) throws Exception{
        if (UserDto.checkPhoneFormat(phone))
            setPhone(phone);
        else
            throw new Exception("The email is not valid");
    }
}