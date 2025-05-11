package com.example.storage_manager.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private List<String> roles;

}
