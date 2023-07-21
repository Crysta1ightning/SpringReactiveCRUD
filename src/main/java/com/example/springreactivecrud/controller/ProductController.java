package com.example.springreactivecrud.controller;

import com.example.springreactivecrud.entity.Product;
import com.example.springreactivecrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/hello")
    public String hello() { return "hello"; }

    @GetMapping("/findAll")
    public ResponseEntity<Flux<Product>> getAllProduct() {
        try {
            Flux<Product> productFlux = productService.getAllProduct();
            return new ResponseEntity<>(productFlux, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Mono<Product>> getProduct(@PathVariable int id) {
        try {
            Mono<Product> productMono = productService.getProduct(id);
            return new ResponseEntity<>(productMono, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Mono<Product>> insertProduct(@RequestBody Product product) {
        try {
            Mono<Product> productMono = productService.insertProduct(product);
            return new ResponseEntity<>(productMono, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Mono<Product>> updateProduct(@RequestBody Product product, @PathVariable int id) {
        try {
            Mono<Product> productMono = productService.updateProduct(product, id);
            return new ResponseEntity<>(productMono, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Mono<Void>> deleteProduct(@PathVariable int id) {
        try {
            Mono<Void> productMono = productService.deleteProduct(id);
            return new ResponseEntity<>(productMono, HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Mono<Void>> deleteAllProduct() {
        try {
            Mono<Void> productMono = productService.deleteAllProduct();
            return new ResponseEntity<>(productMono, HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
