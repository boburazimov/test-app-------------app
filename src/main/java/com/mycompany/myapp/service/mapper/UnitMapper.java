package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Unit;
import com.mycompany.myapp.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unit} and its DTO {@link UnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {}
