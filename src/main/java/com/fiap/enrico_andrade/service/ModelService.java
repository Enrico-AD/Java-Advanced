package com.fiap.enrico_andrade.service;

import com.fiap.enrico_andrade.dto.ModelDTO;

import java.util.List;

public interface ModelService {
    ModelDTO findById(Integer id);
    List<ModelDTO> findAll();
    ModelDTO save(ModelDTO dto);
    ModelDTO update(Integer id, ModelDTO dto);
    void delete(Integer id);
}
