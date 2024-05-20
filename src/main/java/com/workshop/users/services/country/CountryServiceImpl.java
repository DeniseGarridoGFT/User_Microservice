package com.workshop.users.services.country;

import com.workshop.users.api.dto.CountryDto;
import com.workshop.users.exceptions.CountryNotFoundException;
import com.workshop.users.model.CountryEntity;
import com.workshop.users.repositories.CountryDAORepository;
import org.springframework.stereotype.Service;

import static jdk.dynalink.linker.support.Guards.isNotNull;

@Service
public class CountryServiceImpl implements CountryService {
    private CountryDAORepository countryDAORepository;

    public CountryServiceImpl(CountryDAORepository countryDAORepository) {
        this.countryDAORepository = countryDAORepository;
    }

    @Override
    public CountryDto getCountryById(Long id) throws RuntimeException {
        isNotNull(id);
        return CountryEntity.fromEntity(countryDAORepository.findById(id).orElseThrow());
    }

    @Override
    public CountryDto getCountryByName(String name) throws CountryNotFoundException   {
        try {
            return CountryEntity.fromEntity(countryDAORepository.findByName(name).orElseThrow());

        }catch (RuntimeException ex){
            throw  new CountryNotFoundException("Sorry! We're not in that country yet. We deliver to España, Estonia, Finalndia");
        }
    }



    public void isNotNull(Object id) throws RuntimeException{
        if (id==null){
            throw new RuntimeException("Request not valid");
        }
    }
}
