package com.fiap.enrico_andrade.repository;

import com.fiap.enrico_andrade.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Integer> {
    Optional<Tenant> findByCpf(String cpf);
}
