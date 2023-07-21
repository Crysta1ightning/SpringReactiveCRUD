package com.example.springreactivecrud.repo;

import com.example.springreactivecrud.entity.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ProductRepo extends R2dbcRepository<Product, Integer> {
    Mono<Void> deleteProductByName(String name);
}
