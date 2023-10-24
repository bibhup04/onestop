package com.service.onestopauth.repository;
import java.util.List;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.service.onestopauth.entity.User;




public interface UserRepository extends CrudRepository<User, Long>{
    public Optional<User> findByName(String name);

    List<User> findAll();
}