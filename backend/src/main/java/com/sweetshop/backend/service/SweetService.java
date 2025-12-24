package com.sweetshop.backend.service;

import com.sweetshop.backend.config.ModelMapperConfig;
import com.sweetshop.backend.dto.SweetResponse;
import com.sweetshop.backend.entity.Sweet;
import com.sweetshop.backend.exception.OutofStockException;
import com.sweetshop.backend.repository.SweetRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SweetService {

    private final SweetRepository sweetRepository;
    public final ModelMapper modelMapper;

    @Value("${spring.application.discount}")
    private double discount;

    public Sweet addSweet(Sweet sweet, MultipartFile file) throws IOException {

        sweet.setImageName(file.getOriginalFilename());
        sweet.setImageType(file.getContentType());
        sweet.setImage(file.getBytes());
        return sweetRepository.save(sweet);
    }

    public List<Sweet> getAllSweets()
    {
        List<Sweet> res = sweetRepository.findAll()
                .stream()
                .map(sweet -> discount(sweet,discount))
                .collect(Collectors.toList());
        return res;
    }

    public Sweet discount(Sweet sweet,double DISCOUNT)
    {
        //created copy object to control dirty checking of real value in database.
        Sweet copysweet = new Sweet();
        copysweet.setId(sweet.getId());
        copysweet.setQuantity(sweet.getQuantity());
        copysweet.setName(sweet.getName());
        copysweet.setPrice(sweet.getPrice()*((100.00-DISCOUNT)/100.00));
        return copysweet;
    }

    public List<Sweet> searchSweets(String name, String category, Double minPrice, Double maxPrice) {
        return sweetRepository.searchSweets(name, category, minPrice, maxPrice);
    }

    public Sweet updateSweet(Long id, Sweet sweetDetails, MultipartFile file) throws IOException {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        sweet.setName(sweetDetails.getName());
        sweet.setPrice(sweetDetails.getPrice());
        sweet.setQuantity(sweetDetails.getQuantity());
        sweet.setCategory(sweetDetails.getCategory());
        sweet.setDescription(sweetDetails.getDescription());

        if (file != null && !file.isEmpty()) {
            sweet.setImageName(file.getOriginalFilename());
            sweet.setImageType(file.getContentType());
            sweet.setImage(file.getBytes());
        }

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
        else
        {
            sweet.setQuantity(sweet.getQuantity() - 1);
            sweet.setSoldCount(sweet.getSoldCount() + 1);
        }

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

    public Sweet getSweetById(Long id) {
        return sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));
    }

    public SweetResponse checkQuantity(Long id)
    {
        Sweet sweet = getSweetById(id);

        if(sweet.getQuantity() <= 0)
        {
            throw new OutofStockException("No quantity left. Please restock.");
        }

        return modelMapper.map(sweet,SweetResponse.class);

    }

}
