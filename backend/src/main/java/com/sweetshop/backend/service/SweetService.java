package com.sweetshop.backend.service;

import com.sweetshop.backend.config.ModelMapperConfig;
import com.sweetshop.backend.dto.SweetResponse;
import com.sweetshop.backend.dto.trendingSweetResponse;
import com.sweetshop.backend.entity.Sweet;
import com.sweetshop.backend.exception.InvalidRestockAmountException;
import com.sweetshop.backend.exception.OutofStockException;
import com.sweetshop.backend.exception.SweetNotFoundException;
import com.sweetshop.backend.repository.SweetRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
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
                .orElseThrow(() -> new SweetNotFoundException(id));

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
            throw new SweetNotFoundException(id);
        }
        sweetRepository.deleteById(id);
    }

    public Sweet purchaseSweet(Long id) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found with id: " + id));

        if (sweet.getQuantity() <= 0) {
            throw new OutofStockException("Sweet is out of Stock");
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
            throw new InvalidRestockAmountException();
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
            throw new OutofStockException("No Quantity left, Please Restock");
        }

        return modelMapper.map(sweet,SweetResponse.class);

    }

    @Transactional(readOnly = true)
    public List<Sweet> trendingSweets()
    {
        return new ArrayList<>(sweetRepository.findTop2ByOrderBySoldCountDescNameAsc());
    }

    @Transactional(rollbackFor = Exception.class)
    public void purchaseBatch(List<Long> ids) {
        for (Long id : ids) {
            Sweet sweet = sweetRepository.findById(id).orElseThrow();
            if (sweet.getQuantity() > 0) {
                sweet.setQuantity(sweet.getQuantity() - 1);
                sweet.setSoldCount(sweet.getSoldCount() + 1);
                sweetRepository.save(sweet);
            } else {
                throw new OutofStockException("Sweet " + sweet.getName() + " is out of stock!");
            }
        }
    }

}
