package com.fiap.enrico_andrade.service;

import com.fiap.enrico_andrade.dto.YardDTO;

import java.util.List;

public interface YardService {
    List<YardDTO> findAll();
    YardDTO findById(Integer id);
}
