package com.fiap.enrico_andrade.repository;

import com.fiap.enrico_andrade.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Integer> {
    @Query("SELECT m FROM Motorcycle m JOIN Status s ON s.motorcycle.id = m.id " +
            "WHERE s.description = 'Liberada'")
    List<Motorcycle> findByStatus(String status);
    boolean existsByModelId(Integer modelId);
}
