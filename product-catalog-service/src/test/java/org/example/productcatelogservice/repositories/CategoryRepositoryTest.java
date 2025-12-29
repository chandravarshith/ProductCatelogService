package org.example.productcatelogservice.repositories;

import jakarta.transaction.Transactional;
import org.example.productcatelogservice.models.Category;
import org.example.productcatelogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    public void testLoading(){
        Category category = categoryRepository.findById(1L).get();
        System.out.println(category.getName());
        for(Product product : category.getProducts()){
            System.out.println(product.getName());
        }
    }

    @Test
    @Transactional
    public void testAllCategoriesLoading(){
        List<Category> categories = categoryRepository.findAll();
        for(Category category : categories){
            System.out.println(category.getName());
            for(Product product : category.getProducts()){
                System.out.println(product.getName());
            }
        }
    }
}