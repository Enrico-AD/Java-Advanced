package com.fiap.enrico_andrade.repository;

import com.fiap.enrico_andrade.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Integer> {
    Optional<Model> findByName(String name);
}
