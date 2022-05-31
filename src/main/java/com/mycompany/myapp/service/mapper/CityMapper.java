package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.City;
import com.mycompany.myapp.domain.Region;
import com.mycompany.myapp.service.dto.CityDTO;
import com.mycompany.myapp.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {
    @Mapping(target = "region", source = "region", qualifiedByName = "regionId")
    CityDTO toDto(City s);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);
}
