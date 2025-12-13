package com.sweetshop.backend.service;

import com.sweetshop.backend.entity.Sweet;
import com.sweetshop.backend.repository.SweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SweetServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SweetRepository sweetRepository;

    @InjectMocks
    private  SweetService sweetService;

    @Test
    public void testAddSweet_Success()
    {
        Sweet inputSweet = new Sweet();
        inputSweet.setName("Gulab Jamun");
        inputSweet.setPrice(50.0);
        inputSweet.setImageUrl("images/gulab.jpg");

        Sweet savedSweet = new Sweet();
        savedSweet.setId(1L);
        savedSweet.setName("Gulab Jamun");
        savedSweet.setDescription("An elegant Gulab Jamun");
        savedSweet.setPrice(50.50);
        savedSweet.setQuantity(50);
        savedSweet.setCategory("sweet");
        savedSweet.setImageUrl("images/gulab.jpg");

        when(sweetRepository.save(inputSweet)).thenReturn(savedSweet);

        Sweet result = sweetService.addSweet(inputSweet);

        assertNotNull(result, "Service returned null!");
        assertEquals("Gulab Jamun", result.getName());
        assertEquals("images/gulab.jpg", result.getImageUrl());
    }
}
