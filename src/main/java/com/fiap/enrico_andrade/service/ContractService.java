package com.fiap.enrico_andrade.service;

import com.fiap.enrico_andrade.dto.ContractDTO;
import com.fiap.enrico_andrade.dto.ContractUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface ContractService {

    List<ContractDTO> listAll();

    Optional<ContractUpdateDTO> findById(Integer id);

    Optional<ContractDTO> findDetailById(Integer id);

    ContractDTO updateContract(Integer id, ContractUpdateDTO contractUpdateDTO);

    void finalizeContract(Integer id);

    void createContract(ContractUpdateDTO contractDTO);
}
