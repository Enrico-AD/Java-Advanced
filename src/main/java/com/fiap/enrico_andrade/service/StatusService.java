package com.fiap.enrico_andrade.service;


import com.fiap.enrico_andrade.dto.StatusDTO;
import com.fiap.enrico_andrade.entity.Status;

import java.util.List;
import java.util.Optional;

public interface StatusService {
    List<Status> findAll();

    List<String> findAllDescriptions();

    Status findById(Integer id);

    Status save(Status status);

    void deleteById(Integer id);

    Optional<StatusDTO> findLastStatusByContractId(Integer contractId);

    List<StatusDTO> getAvailableStatuses();

    List<String> getAvailableDescriptions();
}
