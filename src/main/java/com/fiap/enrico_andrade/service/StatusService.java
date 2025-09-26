package com.fiap.enrico_andrade.service;


import com.fiap.enrico_andrade.entity.Status;

import java.util.List;

public interface StatusService {

    List<Status> findAll();

    List<String> findAllDescriptions();

    Status findById(Long id);

    Status save(Status status);

    void deleteById(Long id);

    Status findLastStatusByContractId(Integer contractId);
}
