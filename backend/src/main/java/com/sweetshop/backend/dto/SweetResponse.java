package com.sweetshop.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SweetResponse {
    private String name;
    private Double price;
    private String category;
    private Integer quantity;

}
