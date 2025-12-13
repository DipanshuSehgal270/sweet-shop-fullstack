package com.sweetshop.backend.controller;

import com.sweetshop.backend.entity.Sweet;
import com.sweetshop.backend.service.SweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SweetController {

    private final SweetService sweetService;

    @PostMapping("/sweets")
    private ResponseEntity<Sweet> addSweet(@RequestBody Sweet sweet)
    {
        Sweet toSave = sweetService.addSweet(sweet);
        return new ResponseEntity<>(toSave, HttpStatus.CREATED);
    }
}
