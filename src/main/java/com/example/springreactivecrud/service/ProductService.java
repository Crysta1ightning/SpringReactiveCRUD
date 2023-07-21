package com.example.springreactivecrud.service;


import com.example.springreactivecrud.entity.Product;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    public Flux<Product> getAllProduct();
    public Mono<Product> getProduct(Integer id);
    public Mono<Product> insertProduct(Product product);
    public Mono<Product> updateProduct(Product product, Integer id);
    public Mono<Void> deleteProduct(Integer id);
    public Mono<Void> deleteAllProduct();
}

