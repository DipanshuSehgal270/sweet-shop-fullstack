package com.sweetshop.backend.controller;

import com.sweetshop.backend.dto.SweetResponse;
import com.sweetshop.backend.dto.trendingSweetResponse;
import com.sweetshop.backend.entity.Sweet;
import com.sweetshop.backend.service.SweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sweets")
@CrossOrigin(origins = "http://localhost:5174")
@RequiredArgsConstructor
public class SweetController {

    private final SweetService sweetService;

    @GetMapping
    public ResponseEntity<List<Sweet>> getAllSweets() {
        return new ResponseEntity<>(sweetService.getAllSweets(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Sweet>> searchSweets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return new ResponseEntity<>(
                sweetService.searchSweets(name, category, minPrice, maxPrice),
                HttpStatus.OK);
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Sweet> addSweet(
            @RequestPart("sweet") String sweetJson,
            @RequestPart("image") MultipartFile image
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Sweet sweet = mapper.readValue(sweetJson, Sweet.class);
        Sweet createdSweet = sweetService.addSweet(sweet, image);
        return new ResponseEntity<>(createdSweet, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getSweetImage(@PathVariable Long id) {
        Sweet sweet = sweetService.getSweetById(id);
        if (sweet.getImage() == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_TYPE, sweet.getImageType())
                .body(sweet.getImage());
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Sweet> updateSweet(
            @PathVariable Long id,
            @RequestPart("sweet") String sweetJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Sweet sweetDetails = mapper.readValue(sweetJson, Sweet.class);
        Sweet updatedSweet = sweetService.updateSweet(id, sweetDetails, image);
        return new ResponseEntity<>(updatedSweet, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSweet(@PathVariable Long id) {
        sweetService.deleteSweet(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<Sweet> purchaseSweet(@PathVariable Long id) {
        Sweet updatedSweet = sweetService.purchaseSweet(id);
        return new ResponseEntity<>(updatedSweet, HttpStatus.OK);
    }

    @PostMapping("/{id}/restock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sweet> restockSweet(@PathVariable Long id, @RequestParam Integer amount) {
        Sweet updatedSweet = sweetService.restockSweet(id, amount);
        return new ResponseEntity<>(updatedSweet, HttpStatus.OK);
    }

    @GetMapping("/stock/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SweetResponse> checkStock(@PathVariable Long id)
    {
        SweetResponse response = sweetService.checkQuantity(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<Sweet>> trendingSweets(){
        List<Sweet>  response = new ArrayList<>(sweetService.trendingSweets());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/purchase/batch")
    public ResponseEntity<String> purchaseBatch(@RequestBody List<Long> ids) {
        try {
            sweetService.purchaseBatch(ids);
            return ResponseEntity.ok("Purchase successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}