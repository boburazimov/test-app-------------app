package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.City;
import com.mycompany.myapp.domain.District;
import com.mycompany.myapp.service.dto.CityDTO;
import com.mycompany.myapp.service.dto.DistrictDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {
    @Mapping(target = "city", source = "city", qualifiedByName = "cityId")
    DistrictDTO toDto(District s);

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);
}
