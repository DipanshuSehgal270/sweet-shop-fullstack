package com.sweetshop.backend.service;

import com.sweetshop.backend.entity.Sweet;
import com.sweetshop.backend.repository.SweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SweetServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SweetRepository sweetRepository;

    @InjectMocks
    private  SweetService sweetService;

    @Test
    public void testAddSweet_Success() throws java.io.IOException {

        Sweet inputSweet = new Sweet();
        inputSweet.setName("Gulab Jamun");
        inputSweet.setPrice(50.0);

        Sweet savedSweet = new Sweet();
        savedSweet.setId(1L);
        savedSweet.setName("Gulab Jamun");
        savedSweet.setPrice(50.0);

        org.springframework.web.multipart.MultipartFile mockFile =
                new org.springframework.mock.web.MockMultipartFile(
                        "image", "gulab.jpg", "image/jpeg", "test-image".getBytes()
                );

        when(sweetRepository.save(inputSweet)).thenReturn(savedSweet);

        Sweet result = sweetService.addSweet(inputSweet, mockFile);

        assertNotNull(result, "Service returned null!");
        assertEquals("Gulab Jamun", result.getName());
    }
}
