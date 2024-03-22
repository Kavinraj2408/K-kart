package com.kavin.productservice.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.kavin.productservice.dto.ProductDTO;
import com.kavin.productservice.entity.Product;

@Mapper
@Component
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true) // Ignore mapping id from DTO to entity
    Product productDTOToProduct(ProductDTO productDTO);

    ProductDTO productToProductDTO(Product product);
}
