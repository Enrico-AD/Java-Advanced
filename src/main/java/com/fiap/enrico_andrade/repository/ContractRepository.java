package com.fiap.enrico_andrade.repository;

import com.fiap.enrico_andrade.entity.Contract;
import com.fiap.enrico_andrade.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    @Query("""
                SELECT c, s 
                FROM Contract c
                JOIN Status s ON s.contract = c
                WHERE s.timestamp = (
                    SELECT MAX(s2.timestamp)
                    FROM Status s2
                    WHERE s2.contract = c
                )
            """)
    List<Object[]> findContractsWithLatestStatus();

    boolean existsByMotorcycleId(Integer motorcycleId);
}
