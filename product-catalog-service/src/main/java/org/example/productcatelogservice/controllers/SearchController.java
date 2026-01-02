package org.example.productcatelogservice.controllers;

import org.example.productcatelogservice.dtos.SearchRequestDto;
import org.example.productcatelogservice.models.Product;
import org.example.productcatelogservice.services.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    @PostMapping
    public ResponseEntity<Page<Product>> searchProduct(@RequestBody SearchRequestDto searchRequestDto){
        Page<Product> page = this.searchService.search(
                searchRequestDto.getQuery(),
                searchRequestDto.getPageSize(),
                searchRequestDto.getPageNumber(),
                searchRequestDto.getSortParams()
        );
        return ResponseEntity.ok(page);
    }
}
