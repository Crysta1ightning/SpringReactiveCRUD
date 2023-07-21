package com.example.springreactivecrud.service;

import com.example.springreactivecrud.entity.Product;
import com.example.springreactivecrud.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepo productRepo;
    @Override
    public Flux<Product> getAllProduct() {
        return  productRepo.findAll();
    }

    @Override
    public Mono<Product> getProduct(Integer id) {
        return  productRepo.findById(id);
    }

    @Override
    public Mono<Product> insertProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Mono<Product> updateProduct(Product product, Integer id) {
        return productRepo.findById(id)
                .map (
                        (c)-> {
                            c.setName(product.getName());
                            c.setPrice(product.getPrice());
                            return c;
                        }).flatMap(c->productRepo.save(c));
    }

    @Override
    public Mono<Void> deleteProduct(Integer id) {
        return productRepo.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAllProduct() {
        return productRepo.deleteAll();
    }
}
