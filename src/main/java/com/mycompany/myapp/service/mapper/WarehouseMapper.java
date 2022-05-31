package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Group;
import com.mycompany.myapp.domain.PriceType;
import com.mycompany.myapp.domain.Warehouse;
import com.mycompany.myapp.service.dto.GroupDTO;
import com.mycompany.myapp.service.dto.PriceTypeDTO;
import com.mycompany.myapp.service.dto.WarehouseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Warehouse} and its DTO {@link WarehouseDTO}.
 */
@Mapper(componentModel = "spring")
public interface WarehouseMapper extends EntityMapper<WarehouseDTO, Warehouse> {
    @Mapping(target = "priceType", source = "priceType", qualifiedByName = "priceTypeId")
    @Mapping(target = "group", source = "group", qualifiedByName = "groupId")
    WarehouseDTO toDto(Warehouse s);

    @Named("priceTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PriceTypeDTO toDtoPriceTypeId(PriceType priceType);

    @Named("groupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GroupDTO toDtoGroupId(Group group);
}
