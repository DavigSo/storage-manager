package com.example.storage_manager.mapper;
import com.example.storage_manager.dto.ProductResponseDTO;
import com.example.storage_manager.dto.UserRegisterDTO;
import com.example.storage_manager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ProductResponseDTO toResponseDTO(User user);
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRegisterDTO dto);
}
