package com.fiap.enrico_andrade.service;


import com.fiap.enrico_andrade.dto.StatusDTO;
import com.fiap.enrico_andrade.entity.Status;
import com.fiap.enrico_andrade.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    @Override
    public List<String> findAllDescriptions() {
        return statusRepository.findAll().stream()
                .map(Status::getDescription)
                .distinct()
                .toList();
    }

    @Override
    public Status findById(Integer id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status não encontrado: " + id));
    }

    @Override
    public Status save(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public void deleteById(Integer id) {
        if (!statusRepository.existsById(id)) {
            throw new RuntimeException("Status não encontrado: " + id);
        }
        statusRepository.deleteById(id);
    }

    @Override
    public Optional<StatusDTO> findLastStatusByContractId(Integer contractId) {
        return Optional.ofNullable(statusRepository.findLastStatusByContractId(contractId))
                .map(status -> new StatusDTO(
                        status.getId(),
                        status.getDescription(),
                        status.getTimestamp()
                ));
    }

    @Override
    public List<StatusDTO> getAvailableStatuses() {
        return List.of(
                new StatusDTO(null, "Liberada"),
                new StatusDTO(null, "Aguardando liberação"),
                new StatusDTO(null, "Em manutenção"),
                new StatusDTO(null, "Irreparável")
        );
    }

    @Override
    public List<String> getAvailableDescriptions() {
        return List.of("Liberada", "Aguardando liberação", "Em manutenção", "Irreparável");
    }
}
