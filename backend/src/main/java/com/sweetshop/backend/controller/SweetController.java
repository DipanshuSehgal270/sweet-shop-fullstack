package com.sweetshop.backend.controller;

import com.sweetshop.backend.entity.Sweet;
import com.sweetshop.backend.service.SweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SweetController {

    private final SweetService sweetService;

    @PostMapping(value = "/sweets", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Sweet> addSweet(
            @RequestPart("sweet") String sweetJson,
            @RequestPart("image") MultipartFile image
    ) throws java.io.IOException {

        ObjectMapper mapper = new ObjectMapper();
        Sweet sweet = mapper.readValue(sweetJson, Sweet.class);

        Sweet toSave = sweetService.addSweet(sweet, image);
        return new ResponseEntity<>(toSave, HttpStatus.CREATED);
    }

    @GetMapping("/sweets")
    private ResponseEntity<java.util.List<Sweet>> getAllSweets()
    {
        return new ResponseEntity<>(sweetService.getAllSweets(), HttpStatus.OK);
    }

    @GetMapping("/sweets/search")
    public ResponseEntity<java.util.List<Sweet>> searchSweets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        java.util.List<Sweet> result = sweetService.searchSweets(name, category, minPrice, maxPrice);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/sweets/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Sweet> updateSweet(
            @PathVariable Long id,
            @RequestPart("sweet") String sweetJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws java.io.IOException {

        ObjectMapper mapper = new ObjectMapper();
        Sweet sweetDetails = mapper.readValue(sweetJson, Sweet.class);

        Sweet updatedSweet = sweetService.updateSweet(id, sweetDetails, image);
        return new ResponseEntity<>(updatedSweet, HttpStatus.OK);
    }

    @DeleteMapping("/sweets/{id}")
    public ResponseEntity<Void> deleteSweet(@PathVariable Long id) {
        sweetService.deleteSweet(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sweets/{id}/purchase")
    public ResponseEntity<Sweet> purchaseSweet(@PathVariable Long id) {
        Sweet updatedSweet = sweetService.purchaseSweet(id);
        return new ResponseEntity<>(updatedSweet, HttpStatus.OK);
    }

    @PostMapping("/sweets/{id}/restock")
    public ResponseEntity<Sweet> restockSweet(@PathVariable Long id, @RequestParam Integer amount) {
        Sweet updatedSweet = sweetService.restockSweet(id, amount);
        return new ResponseEntity<>(updatedSweet, HttpStatus.OK);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getSweetImage(@PathVariable Long id) {
        Sweet sweet = sweetService.getSweetById(id);

        if (sweet.getImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_TYPE, sweet.getImageType())
                .body(sweet.getImage());
    }

}
