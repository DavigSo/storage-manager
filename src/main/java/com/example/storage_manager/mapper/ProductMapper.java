package com.example.storage_manager.mapper;

import com.example.storage_manager.dto.ProductCreateDTO;
import com.example.storage_manager.dto.ProductResponseDTO;
import com.example.storage_manager.dto.ProductUpdateDTO;
import com.example.storage_manager.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDTO toResponseDTO(Product product);
    Product toEntity(ProductCreateDTO dto);
    void updateEntity(@MappingTarget Product target, ProductUpdateDTO dto);
}
