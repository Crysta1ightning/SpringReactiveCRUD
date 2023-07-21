package com.example.springreactivecrud.repo;

import com.example.springreactivecrud.entity.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ProductRepo extends R2dbcRepository<Product, Integer> {

}
