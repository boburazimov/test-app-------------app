package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.City;
import com.mycompany.myapp.domain.District;
import com.mycompany.myapp.domain.Partner;
import com.mycompany.myapp.domain.Region;
import com.mycompany.myapp.service.dto.AddressDTO;
import com.mycompany.myapp.service.dto.CityDTO;
import com.mycompany.myapp.service.dto.DistrictDTO;
import com.mycompany.myapp.service.dto.PartnerDTO;
import com.mycompany.myapp.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "region", source = "region", qualifiedByName = "regionId")
    @Mapping(target = "city", source = "city", qualifiedByName = "cityId")
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    @Mapping(target = "partner", source = "partner", qualifiedByName = "partnerId")
    AddressDTO toDto(Address s);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);

    @Named("partnerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PartnerDTO toDtoPartnerId(Partner partner);
}
