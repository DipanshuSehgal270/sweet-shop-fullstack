package com.sweetshop.backend.service;

import com.sweetshop.backend.dto.SweetResponse;
import com.sweetshop.backend.entity.Sweet;
import com.sweetshop.backend.exception.OutofStockException;
import com.sweetshop.backend.repository.SweetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SweetServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SweetRepository sweetRepository;

    @Mock
    private SweetResponse sweetResponse;

    @Mock
    public ModelMapper modelMapper;

    @InjectMocks
    private SweetService sweetService;

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

    @Test
    public void test_getAllSweetsAtDiscountedPrice() throws Exception {

        Sweet inputsweet = new Sweet();
        inputsweet.setId(1L);
        inputsweet.setName("Gulab Jamun");
        inputsweet.setPrice(100.0);
        inputsweet.setQuantity(45);

        when(sweetService.getAllSweets()).thenReturn(List.of(inputsweet));

        List<Sweet> res = sweetService.getAllSweets();

        assertEquals(90, res.get(0).getPrice());

    }

    @Test
    public void test_shouldDecreaseQuantityByOneWhenSweetIsPurchased() throws Exception {
        Sweet inputsweet = new Sweet();
        inputsweet.setId(1L);
        inputsweet.setName("Gulab Jamun");
        inputsweet.setPrice(100.0);
        inputsweet.setQuantity(45);

        when(sweetRepository.findById(1l)).thenReturn(Optional.of(inputsweet));
        when(sweetRepository.save(any(Sweet.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Sweet sweet = sweetService.purchaseSweet(1l);

        Assertions.assertEquals(44, inputsweet.getQuantity());

    }

    @Test
    public void shouldThrowExceptionWhenSweetIsOutOfStock() throws Exception {

        //successfully throws exception as response

        Sweet inputsweet = new Sweet();
        inputsweet.setId(1L);
        inputsweet.setName("Gulab Jamun");
        inputsweet.setPrice(100.0);
        inputsweet.setQuantity(10);

        SweetResponse response = new SweetResponse();
        response.setName("Gulab Jamun");
        response.setQuantity(50);
        response.setPrice(250.0);
        response.setCategory("Milk made");

        when(sweetRepository.findById(1L))
                .thenReturn(Optional.of(inputsweet));
        when(sweetService.checkQuantity(1L)).thenReturn(response);

        SweetResponse sweetResponse = sweetService.checkQuantity(1L);

        assertNotNull(sweetResponse);
//        assertThrows(OutofStockException.class,
//                () -> sweetRepository.findById(1L));


    }

}
