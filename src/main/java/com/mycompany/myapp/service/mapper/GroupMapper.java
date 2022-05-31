package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Group;
import com.mycompany.myapp.service.dto.GroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Group} and its DTO {@link GroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface GroupMapper extends EntityMapper<GroupDTO, Group> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "groupId")
    GroupDTO toDto(Group s);

    @Named("groupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GroupDTO toDtoGroupId(Group group);
}
