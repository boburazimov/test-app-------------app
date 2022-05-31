package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Currency;
import com.mycompany.myapp.domain.Group;
import com.mycompany.myapp.domain.PriceType;
import com.mycompany.myapp.service.dto.CurrencyDTO;
import com.mycompany.myapp.service.dto.GroupDTO;
import com.mycompany.myapp.service.dto.PriceTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PriceType} and its DTO {@link PriceTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PriceTypeMapper extends EntityMapper<PriceTypeDTO, PriceType> {
    @Mapping(target = "group", source = "group", qualifiedByName = "groupId")
    @Mapping(target = "currency", source = "currency", qualifiedByName = "currencyId")
    PriceTypeDTO toDto(PriceType s);

    @Named("groupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GroupDTO toDtoGroupId(Group group);

    @Named("currencyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrencyDTO toDtoCurrencyId(Currency currency);
}
