package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Attachment;
import com.mycompany.myapp.domain.Brand;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.Group;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.Unit;
import com.mycompany.myapp.service.dto.AttachmentDTO;
import com.mycompany.myapp.service.dto.BrandDTO;
import com.mycompany.myapp.service.dto.CategoryDTO;
import com.mycompany.myapp.service.dto.GroupDTO;
import com.mycompany.myapp.service.dto.ProductDTO;
import com.mycompany.myapp.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "brand", source = "brand", qualifiedByName = "brandId")
    @Mapping(target = "photo", source = "photo", qualifiedByName = "attachmentId")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "group", source = "group", qualifiedByName = "groupId")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitId")
    ProductDTO toDto(Product s);

    @Named("brandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BrandDTO toDtoBrandId(Brand brand);

    @Named("attachmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttachmentDTO toDtoAttachmentId(Attachment attachment);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);

    @Named("groupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GroupDTO toDtoGroupId(Group group);

    @Named("unitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitDTO toDtoUnitId(Unit unit);
}
