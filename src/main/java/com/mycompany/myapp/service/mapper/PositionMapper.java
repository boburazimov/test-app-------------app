package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Group;
import com.mycompany.myapp.domain.Position;
import com.mycompany.myapp.service.dto.GroupDTO;
import com.mycompany.myapp.service.dto.PositionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Position} and its DTO {@link PositionDTO}.
 */
@Mapper(componentModel = "spring")
public interface PositionMapper extends EntityMapper<PositionDTO, Position> {
    @Mapping(target = "group", source = "group", qualifiedByName = "groupId")
    PositionDTO toDto(Position s);

    @Named("groupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GroupDTO toDtoGroupId(Group group);
}
