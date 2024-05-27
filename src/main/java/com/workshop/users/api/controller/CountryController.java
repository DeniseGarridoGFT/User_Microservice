package com.workshop.users.api.controller;
import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.services.country.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/country/{id}")
    public ResponseEntity<CountryDto> getCountry(@PathVariable("id") Long id) {
        CountryDto country = countryService.getCountryById(id);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }
}
