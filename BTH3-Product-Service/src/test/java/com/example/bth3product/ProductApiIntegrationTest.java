package com.example.bth3product;

import com.example.bth3product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductApiIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ProductRepository repository;

    @BeforeEach
    void clearDatabase() {
        repository.deleteAll();
    }

    private static final String VALID_PRODUCT = """
        {"name":"Laptop Dell","description":"Laptop hoc tap","price":18500000.00,"quantity":10}
        """;

    @Test
    void createThenGetProduct() throws Exception {
        String location = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON).content(VALID_PRODUCT))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Laptop Dell"))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(header().exists("Location"))
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(18500000.00));
    }

    @Test
    void listUpdateDeleteLifecycle() throws Exception {
        String location = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON).content(VALID_PRODUCT))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop Dell"));

        mockMvc.perform(put(location).contentType(MediaType.APPLICATION_JSON).content("""
                        {"name":"Laptop HP","description":"Da cap nhat","price":19000000.00,"quantity":5}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop HP"))
                .andExpect(jsonPath("$.quantity").value(5));

        mockMvc.perform(delete(location)).andExpect(status().isNoContent());
        mockMvc.perform(get(location)).andExpect(status().isNotFound());
    }

    @Test
    void rejectsInvalidProduct() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"name":"","price":-1.00,"quantity":-2}
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.name").exists())
                .andExpect(jsonPath("$.fields.price").exists())
                .andExpect(jsonPath("$.fields.quantity").exists());
    }

    @Test
    void returns404ForUnknownId() throws Exception {
        mockMvc.perform(get("/products/999999"))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/products/999999"))
                .andExpect(status().isNotFound());
    }
}
