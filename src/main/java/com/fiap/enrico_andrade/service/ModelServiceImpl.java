package com.fiap.enrico_andrade.service;

import com.fiap.enrico_andrade.entity.Model;
import com.fiap.enrico_andrade.repository.ModelRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<Model> findAll() {
        return modelRepository.findAll();
    }
}
