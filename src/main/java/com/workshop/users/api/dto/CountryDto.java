package com.workshop.users.api.dto;

import com.workshop.users.model.CountryEntity;
import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Builder
public class CountryDto {
    private Long id;
    private String name;
    private Float tax;
    private String prefix;
    private String timeZone;


    public static CountryEntity toEntity(CountryDto dto) {
        CountryEntity entity = new CountryEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setTax(dto.getTax());
        entity.setPrefix(dto.getPrefix());
        entity.setTimeZone(dto.getTimeZone());
        return entity;
    }


}
