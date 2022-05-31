package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Brand;
import com.mycompany.myapp.service.dto.BrandDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Brand} and its DTO {@link BrandDTO}.
 */
@Mapper(componentModel = "spring")
public interface BrandMapper extends EntityMapper<BrandDTO, Brand> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "brandId")
    BrandDTO toDto(Brand s);

    @Named("brandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BrandDTO toDtoBrandId(Brand brand);
}
