package com.example.storage_manager.dto;

import com.example.storage_manager.entity.Category;
import com.example.storage_manager.entity.Sex;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ProductCreateDTO {
    @NotBlank
    private String name;
    @NotNull
    private Category category;
    @NotNull
    private Sex sex;
    @Min(0)
    private int quantity;
    private Date ageUse;
    @Min(1)
    private int minAlert;
}