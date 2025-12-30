package org.example.productcatelogservice.controllers;

import org.example.productcatelogservice.dtos.ProductDto;
import org.example.productcatelogservice.models.Product;
import org.example.productcatelogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    private ProductController productController;

    @MockitoBean
    private IProductService productService;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Test
    public void getProductById_whenIdIsValid_thenReturnsProduct() {
        // Arrange
        Long productId = 1L;

        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product A");
        product.setPrice(10000D);

        when(productService.getProductById(productId)).thenReturn(product);

        // Act
        ResponseEntity<ProductDto> productDtoResponseEntity = productController.getProductById(productId);

        // Assert
        assertNotNull(productDtoResponseEntity);
        assertEquals(HttpStatus.OK, productDtoResponseEntity.getStatusCode());
        assertNotNull(productDtoResponseEntity.getBody());
        assertEquals(productId, productDtoResponseEntity.getBody().getId());
        assertEquals("Test Product A", productDtoResponseEntity.getBody().getName());
        assertEquals(10000D, productDtoResponseEntity.getBody().getPrice());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void getProductById_whenIdIsInvalid_thenThrowsIllegalArgumentException() {
        // Arrange
        Long productId = -1L;

        // Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> productController.getProductById(productId));
        assertEquals("Please pass product id > 0", exception.getMessage());
    }

    @Test
    public void getProductById_whenServiceThrowsRuntimeException_thenRuntimeExceptionIsPropagated() {
        // Arrange
        Long productId = 100L;
        when(productService.getProductById(productId))
                .thenThrow(new RuntimeException("Something went wrong"));

        // Act & Arrange
        Exception exception = assertThrows(RuntimeException.class,
                () -> productController.getProductById(productId));
        assertEquals("Something went wrong", exception.getMessage());
    }

    @Test
    public void getProductById_whenCalled_thenDelegatesToServiceWithCorrectId() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product A");
        product.setPrice(10000D);
        when(productService.getProductById(productId)).thenReturn(product);

        // Act
        productController.getProductById(productId);

        // Assert
        verify(productService).getProductById(idCaptor.capture());
        assertEquals(productId, idCaptor.getValue());
    }
}