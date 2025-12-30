package org.example.productcatelogservice.repositories;

import org.example.productcatelogservice.models.Category;
import org.example.productcatelogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void testQueries()
    {
         List<Product> productList = productRepository.findAll();
         System.out.println(productList.get(0));

        List<Product> productList1 = productRepository.findProductByPriceBetween(1D,900D);
        System.out.println(productList1.size());
        System.out.println(productList1.get(0).getId());
        System.out.println(productList1.get(0).getName());

        List<Product> productList2 = productRepository.findProductByIsPrime(true);
        System.out.println(productList2.size());
        System.out.println(productList2.get(0).getId());
        System.out.println(productList2.get(0).getName());

        List<Product> productList3 = productRepository.findProductByOrderByPriceAsc();
        System.out.println(productList3.size());
        System.out.println(productList3.get(0).getName());
        System.out.println(productList3.get(0).getPrice());

        System.out.println(productRepository.findProductDescriptionById(17L));
    }

    @Test
    public void addDataToAWSTables() {
        Product product1  = new Product();
        product1.setId(10L);
        product1.setName("Melody");
        product1.setPrice(2D);
        Category category = new Category();
        category.setId(101L);
        category.setName("Toffees");
        product1.setCategory(category);

        Product product2  = new Product();
        product2.setId(12L);
        product2.setName("Eclairs");
        product2.setPrice(1D);
        product2.setCategory(category);
        productRepository.save(product1);
        productRepository.save(product2);

    }
}