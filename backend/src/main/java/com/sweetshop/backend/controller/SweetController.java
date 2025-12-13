package com.sweetshop.backend.controller;

import com.sweetshop.backend.service.SweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/api")
@RequiredArgsConstructor
public class SweetController {

    private final SweetService sweetService;

//    @PostMapping("/sweets")
//    private ResponseEntity<?> addSweet()
//    {
//
//    }
}
