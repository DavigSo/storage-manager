package com.example.storage_manager.dto;

import com.example.storage_manager.entity.Category;
import com.example.storage_manager.entity.Sex;
import lombok.Data;

import java.util.Date;

@Data
public class ProductUpdateDTO {
    private String name;
    private Category category;
    private Sex sex;
    private int quantity;
    private Date ageUse;
    private int minAlert;
}