package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Attachment;
import com.mycompany.myapp.service.dto.AttachmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {}
