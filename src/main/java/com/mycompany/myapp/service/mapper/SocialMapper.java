package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Social;
import com.mycompany.myapp.service.dto.SocialDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Social} and its DTO {@link SocialDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocialMapper extends EntityMapper<SocialDTO, Social> {}
