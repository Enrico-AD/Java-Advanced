package com.fiap.enrico_andrade.service;


import com.fiap.enrico_andrade.dto.MotorcycleDTO;
import com.fiap.enrico_andrade.dto.YardDTO;
import com.fiap.enrico_andrade.entity.Model;
import com.fiap.enrico_andrade.entity.Motorcycle;
import com.fiap.enrico_andrade.entity.Status;
import com.fiap.enrico_andrade.entity.Yard;
import com.fiap.enrico_andrade.repository.ModelRepository;
import com.fiap.enrico_andrade.repository.MotorcycleRepository;
import com.fiap.enrico_andrade.repository.StatusRepository;
import com.fiap.enrico_andrade.repository.YardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MotorcycleServiceImpl implements MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final StatusRepository statusRepository;
    private final YardRepository yardRepository;
    private final ModelRepository modelRepository;

    public MotorcycleServiceImpl(
            MotorcycleRepository motorcycleRepository,
            StatusRepository statusRepository,
            YardRepository yardRepository,
            ModelRepository modelRepository
    ) {
        this.motorcycleRepository = motorcycleRepository;
        this.statusRepository = statusRepository;
        this.yardRepository = yardRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    public List<MotorcycleDTO> findAll() {
        return motorcycleRepository.findAll().stream()
                .map(m -> {
                    Status latestStatus = statusRepository
                            .findTopByMotorcycleIdOrderByTimestampDesc(m.getId())
                            .orElse(null);

                    return toDTO(m, latestStatus);
                })
                .toList();
    }

    @Override
    public MotorcycleDTO findById(Integer id) {
        Motorcycle m = motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada: " + id));

        Status latestStatus = statusRepository
                .findTopByMotorcycleIdOrderByTimestampDesc(m.getId())
                .orElse(null);

        return toDTO(m, latestStatus);
    }

    @Override
    public MotorcycleDTO save(MotorcycleDTO dto) {
        Motorcycle entity = new Motorcycle();
        entity.setId(dto.getId());
        entity.setLicensePlate(dto.getLicensePlate());

        Model model = new Model();
        model.setName(dto.getModel());
        entity.setModel(model);

        if (dto.getYard() != null) {
            Yard yard = new Yard();
            yard.setId(dto.getYard().getId());
            entity.setYard(yard);
        }

        Motorcycle saved = motorcycleRepository.save(entity);

        Status status = new Status();
        status.setDescription(dto.getStatus() != null ? dto.getStatus() : "Liberada");
        status.setTimestamp(LocalDateTime.now());
        status.setMotorcycle(saved);
        statusRepository.save(status);

        return toDTO(saved, status);
    }

    @Override
    public void updateDTO(Integer id, MotorcycleDTO dto) {
        Motorcycle entity = motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        entity.setLicensePlate(dto.getLicensePlate());
        entity.setChassis(dto.getChassis());
        Model model = modelRepository.findByName(dto.getModel())
                .orElseGet(() -> {
                    Model newModel = new Model();
                    newModel.setName(dto.getModel());
                    return modelRepository.save(newModel);
                });

        entity.setModel(model);

        Yard yard = yardRepository.findById(dto.getYard().getId())
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
        entity.setYard(yard);

        // criar novo status quando troca
        Status status = new Status();
        status.setDescription(dto.getStatus());
        status.setTimestamp(LocalDateTime.now());
        status.setMotorcycle(entity);

        motorcycleRepository.save(entity);
        statusRepository.save(status);
    }


    @Override
    public void deleteById(Integer id) {
        if (!motorcycleRepository.existsById(id)) {
            throw new RuntimeException("Moto não encontrada: " + id);
        }
        motorcycleRepository.deleteById(id);
    }

    @Override
    public List<MotorcycleDTO> findAvailable() {
        return motorcycleRepository.findByStatus("Liberada")
                .stream()
                .map(m -> {
                    Status status = statusRepository
                            .findTopByMotorcycleIdOrderByTimestampDesc(m.getId())
                            .orElse(null);
                    return toDTO(m, status);
                })
                .toList();
    }

    private MotorcycleDTO toDTO(Motorcycle motorcycle, Status status) {
        YardDTO yardDTO = null;
        if (motorcycle.getYard() != null) {
            yardDTO = new YardDTO(motorcycle.getYard().getId(), motorcycle.getYard().getName(), motorcycle.getYard().getAddress().getStreetName());
        }

        return new MotorcycleDTO(
                motorcycle.getId(),
                motorcycle.getModel() != null ? motorcycle.getModel().getName() : null,
                motorcycle.getLicensePlate(),
                motorcycle.getChassis(),
                yardDTO,
                status != null ? status.getDescription() : "Liberada"
        );
    }
}

