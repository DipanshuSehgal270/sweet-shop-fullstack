package com.sweetshop.backend.service;

import com.sweetshop.backend.entity.Sweet;
import com.sweetshop.backend.repository.SweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SweetService {

    private final SweetRepository sweetRepository;

    public Sweet addSweet(Sweet sweet) {
        return sweetRepository.save(sweet);
    }

    public java.util.List<Sweet> getAllSweets()
    {
        return sweetRepository.findAll();
    }

    public java.util.List<Sweet> searchSweets(String name, String category, Double minPrice, Double maxPrice) {
        return sweetRepository.searchSweets(name, category, minPrice, maxPrice);
    }

    public Sweet updateSweet(Long id, Sweet sweetDetails) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found with id: " + id));

        sweet.setName(sweetDetails.getName());
        sweet.setPrice(sweetDetails.getPrice());
        sweet.setQuantity(sweetDetails.getQuantity());
        sweet.setDescription(sweetDetails.getDescription());
        sweet.setImageUrl(sweetDetails.getImageUrl());
        sweet.setCategory(sweetDetails.getCategory());

        return sweetRepository.save(sweet);
    }

    public void deleteSweet(Long id) {
        //void
    }

}
