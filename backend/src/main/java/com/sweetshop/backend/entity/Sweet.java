package com.sweetshop.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "sweets")
public class Sweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    //    private String imageUrl;
    @Lob
    @Column(length = 1000000)
    private byte[] image;
    private String imageName;
    private String imageType;
    private String category;
    private Integer soldCount = 0;

    @Min(value = 0, message = "Price must be positive")
    private double price;
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    //dicount

    ///  discount get method  save that price of discount int the database .

}
