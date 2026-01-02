package org.example.productcatelogservice.services;

import org.example.productcatelogservice.dtos.SortParam;
import org.example.productcatelogservice.models.Product;
import org.example.productcatelogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageSearchService implements ISearchService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> search(String query, Integer pageSize, Integer pageNumber, List<SortParam> sortParams) {
//        Sort sort = Sort.by("price").and(Sort.by("id").descending());
        Sort sort = null;
        if(!sortParams.isEmpty()){
            sort = Sort.by(Sort.Direction.valueOf(sortParams.get(0).getSortOrder().name()),
                    sortParams.get(0).getSortBy());
        }

        for(int i=1;i<sortParams.size();i++){
            sort = sort.and(Sort.by(Sort.Direction.valueOf(sortParams.get(i).getSortOrder().name()),
                    sortParams.get(i).getSortBy()));
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return this.productRepository.findByNameContainingIgnoreCase(
                query, pageable);
    }
}
