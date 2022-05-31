package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Barcode;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.service.dto.BarcodeDTO;
import com.mycompany.myapp.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Barcode} and its DTO {@link BarcodeDTO}.
 */
@Mapper(componentModel = "spring")
public interface BarcodeMapper extends EntityMapper<BarcodeDTO, Barcode> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    BarcodeDTO toDto(Barcode s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
