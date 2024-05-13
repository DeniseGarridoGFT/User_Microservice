package com.workshop.users.model;

import com.workshop.users.api.dto.UserDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="USERS")
@Data
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private long id;
    @Column(name="NAME")
    private String name;
    @Column(name="LAST_NAME")
    private String lastName;
    @Column(name="EMAIL")
    private String email;
    @Column(name="PASSWORD")
    private String password;
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    private CountryEntity country;
    @Column(name= "FIDELITY_POINTS")
    private Integer fidelityPoints;
    @Column(name= "BIRTH_DATE")
    private Date birthDate;
    @Column(name= "PHONE")
    private String phone;
    @OneToOne
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    private AddressEntity address;


    public static UserDto fromEntity(UserEntity userEntity) {
        Date birthDate = userEntity.getBirthDate();
        return UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .fidelityPoints(userEntity.getFidelityPoints())
                .birthDate(birthDate.getYear()+"/"+birthDate.getMonth()+"/"+birthDate.getDate())
                .phone(userEntity.getPhone())
                .address(AddressEntity.fromEntity(userEntity.getAddress()))
                .country(CountryEntity.fromEntity(userEntity.getCountry()))
                .build();
    }

}
