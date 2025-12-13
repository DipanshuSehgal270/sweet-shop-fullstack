package com.sweetshop.backend.controller;

import com.sweetshop.backend.entity.Sweet;
import com.sweetshop.backend.service.SweetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SweetController.class)
public class SweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SweetService sweetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateSweet_Success() throws Exception {

        Sweet sweet = new Sweet();
        sweet.setName("Kaju Katli");
        sweet.setPrice(100.0);

        Sweet savedSweet = new Sweet();
        savedSweet.setId(1L);
        savedSweet.setName("Kaju Katli");

        when(sweetService.addSweet(any(Sweet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/api/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Kaju Katli"));

    }

    @Test
    public void testGetAllSweets_Success() throws Exception {

        Sweet s1 = new Sweet();
        s1.setId(1L);
        s1.setName("Ladoo");

        Sweet s2 = new Sweet();
        s2.setId(2L);
        s2.setName("Barfi");

        java.util.List<Sweet> sweetList = java.util.Arrays.asList(s1, s2);

        when(sweetService.getAllSweets()).thenReturn(sweetList);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/sweets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Ladoo"));
    }

    @Test
    public void testSearchSweets_Complex() throws Exception {

        Sweet s1 = new Sweet();
        s1.setName("Rasgulla");
        s1.setCategory("Milk Based");
        s1.setPrice(20.0);

        List<Sweet> searchResults = Arrays.asList(s1);

        when(sweetService.searchSweets(null, "Milk", null, 50.0))
                .thenReturn(searchResults);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/sweets/search")
                        .param("category", "Milk")
                        .param("maxPrice", "50.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Rasgulla"));
    }

    @Test
    public void testUpdateSweet_Success() throws Exception {

        Sweet updatedInfo = new Sweet();
        updatedInfo.setName("Kaju Katli Special");
        updatedInfo.setPrice(120.0);

        Sweet resultSweet = new Sweet();
        resultSweet.setId(1L);
        resultSweet.setName("Kaju Katli Special");
        resultSweet.setPrice(120.0);

        when(sweetService.updateSweet(any(Long.class), any(Sweet.class))).thenReturn(resultSweet);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/sweets/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kaju Katli Special"))
                .andExpect(jsonPath("$.price").value(120.0));
    }

    @Test
    public void testDeleteSweet_Success() throws Exception {

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/sweets/{id}", 1L))
                .andExpect(status().isNoContent());

        org.mockito.Mockito.verify(sweetService).deleteSweet(1L);
    }

    @Test
    public void testPurchaseSweet_Success() throws Exception {

        Sweet sweetAfterPurchase = new Sweet();
        sweetAfterPurchase.setId(1L);
        sweetAfterPurchase.setName("Mysore Pak");
        sweetAfterPurchase.setQuantity(9);

        when(sweetService.purchaseSweet(1L)).thenReturn(sweetAfterPurchase);

        mockMvc.perform(post("/api/sweets/{id}/purchase", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(9));
    }
}
