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
        if (!sweetRepository.existsById(id)) {
            throw new RuntimeException("Sweet not found with id: " + id);
        }
        sweetRepository.deleteById(id);
    }

    public Sweet purchaseSweet(Long id) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found with id: " + id));

        if (sweet.getQuantity() <= 0) {
            throw new RuntimeException("Sweet is out of stock!");
        }

        sweet.setQuantity(sweet.getQuantity() - 1);
        return sweetRepository.save(sweet);
    }

    public Sweet restockSweet(Long id, Integer amount) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found with id: " + id));

        if (amount <= 0) {
            throw new RuntimeException("Restock amount must be positive");
        }

        sweet.setQuantity(sweet.getQuantity() + amount);
        return sweetRepository.save(sweet);
    }

}
