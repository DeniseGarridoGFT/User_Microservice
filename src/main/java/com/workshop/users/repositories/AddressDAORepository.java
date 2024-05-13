package com.workshop.users.repositories;

import com.workshop.users.api.dto.AddressDto;
import com.workshop.users.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.text.ParseException;

@Repository
public interface AddressDAORepository extends JpaRepository<AddressEntity,Long> {
}
