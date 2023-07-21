package com.example.springreactivecrud.integration;

import com.example.springreactivecrud.entity.Product;
import com.example.springreactivecrud.repo.ProductRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProductRepo productRepo;

    @AfterEach
    public void before() {
        System.out.println("Before Each Test");
        productRepo.deleteProductByName("Apple").subscribe();
        productRepo.deleteProductByName("Banana").subscribe();
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product(0, "Apple", "12");
        product.setId(null);

        webTestClient.post().uri("/product/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(product.getName())
                .jsonPath("$.price").isEqualTo(product.getPrice());
    }

    @Test
    public void testGetProduct() {
        Product product = new Product(0, "Apple", "12");
        product.setId(null);
        Product savedProduct = productRepo.save(product).block();
        int savedId = savedProduct.getId();

        webTestClient.get().uri("/product/find/{id}", Collections.singletonMap("id", savedId))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedId)
                .jsonPath("$.name").isEqualTo(savedProduct.getName())
                .jsonPath("$.price").isEqualTo(savedProduct.getPrice());
    }

    @Test
    public void testGetAllProducts() {

        Product product = new Product(0, "Apple", "12");
        product.setId(null);
        productRepo.save(product).block();

        Product product2 = new Product(1, "Banana", "14");
        product2.setId(null);
        productRepo.save(product2).block();

        webTestClient.get().uri("/product/findAll")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void testUpdateEmployee() {

        Product product = new Product(0, "Apple", "12");
        product.setId(null);
        Product savedProduct = productRepo.save(product).block();
        int savedId = savedProduct.getId();

        Product updatedProduct = new Product(0, "Banana", "14");
        product.setId(null);

        webTestClient.post().uri("/product/update/{id}", Collections.singletonMap("id", savedId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedProduct), Product.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedId)
                .jsonPath("$.name").isEqualTo(updatedProduct.getName())
                .jsonPath("$.price").isEqualTo(updatedProduct.getPrice());
    }

    @Test
    public void testDeleteEmployee() {

        Product product = new Product(0, "Apple", "12");
        product.setId(null);
        Product savedProduct = productRepo.save(product).block();
        int savedId = savedProduct.getId();

        webTestClient.delete().uri("/product/delete/{id}", Collections.singletonMap("id", savedId))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }
}