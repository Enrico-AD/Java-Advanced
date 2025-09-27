package com.fiap.enrico_andrade.service;


import com.fiap.enrico_andrade.entity.Status;

import java.util.List;

public interface StatusService {

    List<Status> findAll();

    List<String> findAllDescriptions();

    Status findById(Integer id);

    Status save(Status status);

    void deleteById(Integer id);

    Status findLastStatusByContractId(Integer contractId);
}
