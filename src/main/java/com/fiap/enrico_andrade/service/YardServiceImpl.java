package com.fiap.enrico_andrade.service;

import com.fiap.enrico_andrade.dto.YardDTO;
import com.fiap.enrico_andrade.entity.Yard;
import com.fiap.enrico_andrade.repository.YardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YardServiceImpl implements YardService {

    private final YardRepository yardRepository;

    public YardServiceImpl(YardRepository yardRepository) {
        this.yardRepository = yardRepository;
    }

    @Override
    public List<YardDTO> findAll() {
        return yardRepository.findAll()
                .stream()
                .map(YardDTO::new)
                .toList();
    }

    @Override
    public YardDTO findById(Integer id) {
        Yard yard = yardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado: " + id));
        return new YardDTO(yard.getId(), yard.getName(), yard.getAddress().getStreetName());
    }
}