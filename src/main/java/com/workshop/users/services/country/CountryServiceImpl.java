package com.workshop.users.services.country;

import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.exceptions.CountryNotFoundException;
import com.workshop.users.model.CountryEntity;
import com.workshop.users.repositories.CountryDAORepository;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryDAORepository countryDAORepository;

    public CountryServiceImpl(CountryDAORepository countryDAORepository) {
        this.countryDAORepository = countryDAORepository;
    }

    @Override
    public CountryDto getCountryById(Long id) throws CountryNotFoundException{
        isNotNull(id);
        CountryEntity countryEntity = countryDAORepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Sorry! We're not in that country yet. We deliver to España, Estonia, Finlandia, Francia, Italia, Portugal, Grecia"));
        return CountryEntity.fromEntity(countryEntity);
    }

    @Override
    public CountryDto getCountryByName(String name) throws CountryNotFoundException {
        isNotNull(name);
        CountryEntity countryEntity = countryDAORepository.findByName(name)
                .orElseThrow(() -> new CountryNotFoundException("Sorry! We're not in that country yet. We deliver to España, Estonia, Finlandia, Francia, Italia, Portugal, Grecia"));
        return CountryEntity.fromEntity(countryEntity);
    }

    private void isNotNull(Object obj) throws CountryNotFoundException  {
        if (obj == null) {
            throw new CountryNotFoundException("Sorry! We're not in that country yet. We deliver to España, Estonia, Finlandia");
        }
    }
}
