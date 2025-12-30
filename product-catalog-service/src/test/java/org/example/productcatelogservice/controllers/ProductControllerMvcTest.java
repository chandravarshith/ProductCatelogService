package org.example.productcatelogservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.productcatelogservice.dtos.ProductDto;
import org.example.productcatelogservice.models.Product;
import org.example.productcatelogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllProducts_whenProductsExists_thenReturnsProductsList() throws Exception {
        //Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("MacBook Pro");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("MacBook Air");

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        when(productService.getAllProducts()).thenReturn(productList);

        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("MacBook Pro");

        ProductDto productDto2 = new ProductDto();
        productDto2.setId(2L);
        productDto2.setName("MacBook Air");

        List<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(productDto);
        productDtos.add(productDto2);

        String response = objectMapper.writeValueAsString(productDtos);

        // Act & Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("[0].name").value("MacBook Pro"));
    }

    @Test
    public void getAllProducts_whenNoProductsExists_thenReturnsEmptyList() throws Exception {
        // Arrange
        List<Product> productList = new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(productList);

        List<ProductDto> productDtos = new ArrayList<>();
        String response = objectMapper.writeValueAsString(productDtos);

        // Act & Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void createProduct_whenProductNotExists_thenReturnsCreatedProduct() throws Exception {
        // Arrange
        Product product1 = new Product();
        product1.setId(17L);
        product1.setName("MacBook Pro");
        product1.setPrice(99999D);

        when(productService.createProduct(any(Product.class))).thenReturn(product1);

        ProductDto productDto1 = new ProductDto();
        productDto1.setId(17L);
        productDto1.setName("MacBook Pro");
        productDto1.setPrice(99999D);

        String productDtoString = objectMapper.writeValueAsString(productDto1);

        // Act & Assert
        mockMvc.perform(post("/products")
                        .content(productDtoString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(productDtoString))
                .andExpect(jsonPath("$.id").value(17L))
                .andExpect(jsonPath("$.name").value("MacBook Pro"))
                .andExpect(jsonPath("$.price").value(99999D));
    }
}
