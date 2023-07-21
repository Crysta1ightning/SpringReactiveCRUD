package com.example.springreactivecrud.repo;

import com.example.springreactivecrud.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserRepo {

    public Mono<User> findUserByEmail(String email) {
        User newUser = new User(0, "1", "123456");
        return Mono.just(newUser);
    };
}
