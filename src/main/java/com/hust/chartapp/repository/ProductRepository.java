package com.hust.chartapp.repository;

import com.hust.chartapp.domain.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends BaseRepository<Product, String> {

    @Override
    public Product doSthToObj(Object input, String s, Product obj) {
        return null;
    }
}
