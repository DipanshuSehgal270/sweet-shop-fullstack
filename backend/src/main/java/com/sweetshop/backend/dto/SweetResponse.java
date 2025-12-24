package com.sweetshop.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class SweetResponse {

    @NotBlank
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private String category;

    @NotNull
    private Integer quantity;

}
