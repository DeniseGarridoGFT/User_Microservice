package com.workshop.users.services.country;

import com.workshop.users.api.dto.CountryDto;
import org.springframework.stereotype.Service;

@Service
public interface CountryService {
    CountryDto getCountryById(Long id) throws RuntimeException;

}
