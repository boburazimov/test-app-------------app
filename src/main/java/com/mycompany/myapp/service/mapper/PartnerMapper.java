package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Group;
import com.mycompany.myapp.domain.Partner;
import com.mycompany.myapp.domain.Social;
import com.mycompany.myapp.service.dto.GroupDTO;
import com.mycompany.myapp.service.dto.PartnerDTO;
import com.mycompany.myapp.service.dto.SocialDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Partner} and its DTO {@link PartnerDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartnerMapper extends EntityMapper<PartnerDTO, Partner> {
    @Mapping(target = "group", source = "group", qualifiedByName = "groupId")
    @Mapping(target = "social", source = "social", qualifiedByName = "socialId")
    PartnerDTO toDto(Partner s);

    @Named("groupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GroupDTO toDtoGroupId(Group group);

    @Named("socialId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocialDTO toDtoSocialId(Social social);
}
