package com.fiap.enrico_andrade.repository;

import com.fiap.enrico_andrade.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    @Query("SELECT s FROM Status s WHERE s.contract.id = :contractId ORDER BY s.timestamp DESC LIMIT 1")
    Status findLastStatusByContractId(@Param("contractId") Integer contractId);

    Optional<Status> findTopByMotorcycleIdOrderByTimestampDesc(Integer motorcycleId);
}
