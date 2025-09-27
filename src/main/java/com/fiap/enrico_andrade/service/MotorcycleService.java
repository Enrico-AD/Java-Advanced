package com.fiap.enrico_andrade.service;

import com.fiap.enrico_andrade.dto.MotorcycleDTO;

import java.util.List;

public interface MotorcycleService {

    List<MotorcycleDTO> findAll();

    MotorcycleDTO findById(Integer id);

    MotorcycleDTO save(MotorcycleDTO dto);

    void updateDTO(Integer id, MotorcycleDTO dto);

    void deleteById(Integer id);

    List<MotorcycleDTO> findAvailable();
}
