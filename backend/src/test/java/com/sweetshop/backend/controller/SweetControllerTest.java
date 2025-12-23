package com.sweetshop.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetshop.backend.config.JwtUtils;
import com.sweetshop.backend.entity.Sweet;
import com.sweetshop.backend.service.CustomUserDetailsService;
import com.sweetshop.backend.service.SweetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SweetController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private SweetService sweetService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testCreateSweet_Success() throws Exception {
        Sweet sweet = new Sweet();
        sweet.setName("Kaju Katli");
        sweet.setPrice(100.0);

        MockMultipartFile sweetJsonPart = new MockMultipartFile(
                "sweet", "", "application/json", objectMapper.writeValueAsString(sweet).getBytes());

        MockMultipartFile imagePart = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "image-content".getBytes());

        when(sweetService.addSweet(any(Sweet.class), any())).thenReturn(sweet);

        mockMvc.perform(multipart("/api/sweets")
                        .file(sweetJsonPart)
                        .file(imagePart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateSweet_Success() throws Exception {
        Sweet updatedInfo = new Sweet();
        updatedInfo.setName("Updated Barfi");
        updatedInfo.setPrice(120.0);

        Sweet resultSweet = new Sweet();
        resultSweet.setId(1L);
        resultSweet.setName("Updated Barfi");
        resultSweet.setPrice(120.0);

        MockMultipartFile sweetJsonPart = new MockMultipartFile(
                "sweet", "", "application/json", objectMapper.writeValueAsString(updatedInfo).getBytes());

        when(sweetService.updateSweet(eq(1L), any(Sweet.class), any())).thenReturn(resultSweet);

        mockMvc.perform(multipart("/api/sweets/{id}", 1L)
                        .file(sweetJsonPart)
                        .with(request -> { request.setMethod("PUT"); return request; })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Barfi"));
    }

    @Test
    public void testGetAllSweets_Success() throws Exception {
        Sweet s1 = new Sweet();
        s1.setName("Ladoo");
        List<Sweet> sweetList = Arrays.asList(s1);

        when(sweetService.getAllSweets()).thenReturn(sweetList);

        mockMvc.perform(get("/api/sweets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void testDeleteSweet_Success() throws Exception {
        mockMvc.perform(delete("/api/sweets/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(sweetService).deleteSweet(1L);
    }

    @Test
    public void testSearchSweets_Complex() throws Exception {
        Sweet s1 = new Sweet();
        s1.setName("Rasgulla");
        List<Sweet> searchResults = Arrays.asList(s1);

        when(sweetService.searchSweets(null, "Milk", null, 50.0))
                .thenReturn(searchResults);

        mockMvc.perform(get("/api/sweets/search")
                        .param("category", "Milk")
                        .param("maxPrice", "50.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Rasgulla"));
    }

    @Test
    public void testPurchaseSweet_Success() throws Exception {
        Sweet sweet = new Sweet();
        sweet.setQuantity(9);

        when(sweetService.purchaseSweet(1L)).thenReturn(sweet);

        mockMvc.perform(post("/api/sweets/{id}/purchase", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(9));
    }

    @Test
    public void testRestockSweet_Success() throws Exception {
        Sweet sweet = new Sweet();
        sweet.setQuantity(50);

        when(sweetService.restockSweet(1L, 50)).thenReturn(sweet);

        mockMvc.perform(post("/api/sweets/{id}/restock", 1L)
                        .param("amount", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(50));
    }


    @Test
    public void testgetAllSweetsAtDiscountedPrice() throws Exception
    {
        Sweet s1 = new Sweet();
        s1.setId(1L);
        s1.setName("Gulab Jamun");
        s1.setPrice(100.0);

        when(sweetService.getAllSweets()).thenReturn(List.of(s1));

        mockMvc.perform(get("/api/sweets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price").value(100.0));

    }

}