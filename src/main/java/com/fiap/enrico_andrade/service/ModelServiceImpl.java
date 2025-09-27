package com.fiap.enrico_andrade.service;

import com.fiap.enrico_andrade.dto.ModelDTO;
import com.fiap.enrico_andrade.entity.Model;
import com.fiap.enrico_andrade.repository.ModelRepository;
import com.fiap.enrico_andrade.repository.MotorcycleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final MotorcycleRepository motorcycleRepository;

    public ModelServiceImpl(ModelRepository modelRepository,
                            MotorcycleRepository motorcycleRepository) {
        this.modelRepository = modelRepository;
        this.motorcycleRepository = motorcycleRepository;
    }

    @Override
    public List<ModelDTO> findAll() {
        return modelRepository.findAll()
                .stream()
                .map(m -> new ModelDTO(m.getId(), m.getName()))
                .toList();
    }

    @Override
    public ModelDTO findById(Integer id) {
        Model model = modelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado"));
        return new ModelDTO(model.getId(), model.getName());
    }

    @Override
    public ModelDTO save(ModelDTO dto) {
        Model model = new Model();
        model.setName(dto.getName());

        Model saved = modelRepository.save(model);
        return new ModelDTO(saved.getId(), saved.getName());
    }

    @Override
    public ModelDTO update(Integer id, ModelDTO dto) {
        Model model = modelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado"));

        model.setName(dto.getName());
        Model updated = modelRepository.save(model);

        return new ModelDTO(updated.getId(), updated.getName());
    }

    @Override
    public void delete(Integer id) {
        boolean inUse = motorcycleRepository.existsByModelId(id);

        if (inUse) {
            throw new IllegalStateException("Não é possível excluir este modelo, pois já está sendo usado em uma moto.");
        }

        modelRepository.deleteById(id);
    }
}

