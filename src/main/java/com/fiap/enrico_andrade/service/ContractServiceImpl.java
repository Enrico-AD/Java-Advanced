package com.fiap.enrico_andrade.service;

import com.fiap.enrico_andrade.dto.AddressDTO;
import com.fiap.enrico_andrade.dto.ContractDTO;
import com.fiap.enrico_andrade.dto.ContractUpdateDTO;
import com.fiap.enrico_andrade.dto.MotorcycleDTO;
import com.fiap.enrico_andrade.dto.TenantDTO;
import com.fiap.enrico_andrade.entity.Address;
import com.fiap.enrico_andrade.entity.Contract;
import com.fiap.enrico_andrade.entity.Motorcycle;
import com.fiap.enrico_andrade.entity.Status;
import com.fiap.enrico_andrade.entity.Tenant;
import com.fiap.enrico_andrade.repository.AddressRepository;
import com.fiap.enrico_andrade.repository.ContractRepository;
import com.fiap.enrico_andrade.repository.MotorcycleRepository;
import com.fiap.enrico_andrade.repository.StatusRepository;
import com.fiap.enrico_andrade.repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final TenantRepository tenantRepository;
    private final StatusRepository statusRepository;
    private final AddressRepository addressRepository;

    public ContractServiceImpl(
            ContractRepository contractRepository,
            MotorcycleRepository motorcycleRepository,
            TenantRepository tenantRepository,
            StatusRepository statusRepository,
            AddressRepository addressRepository
    ) {
        this.contractRepository = contractRepository;
        this.motorcycleRepository = motorcycleRepository;
        this.tenantRepository = tenantRepository;
        this.statusRepository = statusRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public List<ContractDTO> listAll() {
        List<Contract> contracts = contractRepository.findAll();
        return contracts.stream()
                .map(c -> {
                    Status lastStatus = statusRepository.findLastStatusByContractId(c.getId());
                    String statusDesc = lastStatus != null ? lastStatus.getDescription() : "Sem status";
                    return new ContractDTO(c, statusDesc);
                })
                .toList();
    }

    @Override
    public Optional<ContractUpdateDTO> findById(Integer id) {
        return contractRepository.findById(id)
                .map(contract -> {
                    Status lastStatus = statusRepository.findLastStatusByContractId(contract.getId());
                    String statusDesc = lastStatus != null ? lastStatus.getDescription() : "Sem status";

                    AddressDTO addressDTO = null;
                    if (contract.getTenant() != null && contract.getTenant().getAddress() != null) {
                        addressDTO = new AddressDTO(
                                contract.getTenant().getAddress().getStreetName(),
                                contract.getTenant().getAddress().getNumber(),
                                contract.getTenant().getAddress().getComplement(),
                                contract.getTenant().getAddress().getZipCode()
                        );
                    }

                    TenantDTO tenantDTO = null;
                    if (contract.getTenant() != null) {
                        tenantDTO = new TenantDTO(
                                contract.getTenant().getId(),
                                contract.getTenant().getFullName(),
                                contract.getTenant().getCpf(),
                                addressDTO
                        );
                    }

                    MotorcycleDTO motorcycleDTO = null;
                    if (contract.getMotorcycle() != null) {
                        motorcycleDTO = new MotorcycleDTO(
                                contract.getMotorcycle().getId(),
                                contract.getMotorcycle().getModel().getName(),
                                contract.getMotorcycle().getLicensePlate()
                        );
                    }

                    return new ContractUpdateDTO(
                            contract.getId(),
                            contract.getContractNumber(),
                            contract.getStartDate(),
                            contract.getEndDate(),
                            statusDesc,
                            motorcycleDTO,
                            tenantDTO
                    );
                });
    }

    @Override
    public Optional<ContractDTO> findDetailById(Integer id) {
        return contractRepository.findById(id)
                .map(contract -> {
                    Status lastStatus = statusRepository.findLastStatusByContractId(contract.getId());
                    String statusDesc = lastStatus != null ? lastStatus.getDescription() : "Sem status";
                    return new ContractDTO(contract, statusDesc);
                });
    }

    @Override
    @Transactional
    public void createContract(ContractUpdateDTO dto) {
        Contract contract = new Contract();
        contract.setContractNumber(generateContractNumber());
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());

        contract.setMotorcycle(resolveMotorcycle(dto));
        contract.setTenant(resolveTenant(dto));

        Status status = buildStatus(dto, contract);
        statusRepository.save(status);

        contractRepository.save(contract);
    }

    @Override
    @Transactional
    public ContractDTO updateContract(Integer id, ContractUpdateDTO dto) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        updateContractBasicFields(contract, dto);
        contract.setMotorcycle(resolveMotorcycle(dto));
        contract.setTenant(updateTenant(dto));

        Contract saved = contractRepository.save(contract);

        Status newStatus = buildStatus(dto, saved, contract.getMotorcycle());
        statusRepository.save(newStatus);

        return new ContractDTO(saved, dto.getStatus());
    }

    @Override
    @Transactional
    public void finalizeContract(Integer id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        Motorcycle motorcycle = contract.getMotorcycle();

        Status finalizeStatus = new Status();
        finalizeStatus.setDescription("Liberada");
        finalizeStatus.setTimestamp(LocalDateTime.now());
        finalizeStatus.setContract(contract);
        finalizeStatus.setMotorcycle(motorcycle);
        statusRepository.save(finalizeStatus);
    }

    private void updateContractBasicFields(Contract contract, ContractUpdateDTO dto) {
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());
    }

    private Tenant updateTenant(ContractUpdateDTO dto) {
        Tenant tenant = tenantRepository.findById(dto.getTenant().getId())
                .orElseThrow(() -> new RuntimeException("Locatário não encontrado"));

        tenant.setFullName(dto.getTenant().getFullName());
        tenant.setCpf(dto.getTenant().getCpf());

        Address updatedAddress = updateOrCreateAddress(tenant.getAddress(), dto.getTenant().getAddress());
        tenant.setAddress(updatedAddress);

        return tenantRepository.save(tenant);
    }

    private Address updateOrCreateAddress(Address current, AddressDTO dto) {
        Address address = current != null ? current : new Address();
        address.setStreetName(dto.getStreetName());
        address.setNumber(dto.getNumber());
        address.setComplement(dto.getComplement());
        address.setZipCode(dto.getZipCode());
        return addressRepository.save(address);
    }

    private Status buildStatus(ContractUpdateDTO dto, Contract contract, Motorcycle motorcycle) {
        Status status = new Status();
        status.setDescription(dto.getStatus());
        status.setTimestamp(LocalDateTime.now());
        status.setContract(contract);
        status.setMotorcycle(motorcycle);
        return status;
    }

    private Motorcycle resolveMotorcycle(ContractUpdateDTO dto) {
        if (dto.getMotorcycleId() == null) {
            throw new IllegalArgumentException("É obrigatório selecionar uma motocicleta");
        } else {
            return motorcycleRepository.findById(dto.getMotorcycleId())
                    .orElseThrow(() -> new RuntimeException("Motocicleta não encontrada"));
        }
    }

    private String generateContractNumber() {
        long count = contractRepository.count();
        String year = String.valueOf(LocalDate.now().getYear());
        String sequence = String.format("%03d", count + 1); // próximo número
        return "CTR-" + year + "-" + sequence;
    }

    private Tenant resolveTenant(ContractUpdateDTO dto) {
        if (dto.getTenant() == null) return null;

        String cpf = dto.getTenant().getCpf();
        return tenantRepository.findByCpf(cpf).orElseGet(() -> createTenant(dto));
    }

    private Tenant createTenant(ContractUpdateDTO dto) {
        Tenant tenant = new Tenant();
        tenant.setFullName(dto.getTenant().getFullName());
        tenant.setCpf(dto.getTenant().getCpf());

        if (dto.getTenant().getAddress() != null) {
            tenant.setAddress(createAddress(dto.getTenant().getAddress()));
        }

        return tenantRepository.save(tenant);
    }

    private Address createAddress(AddressDTO dto) {
        Address address = new Address();
        address.setStreetName(dto.getStreetName());
        address.setNumber(dto.getNumber());
        address.setComplement(dto.getComplement());
        address.setZipCode(dto.getZipCode());
        return addressRepository.save(address);
    }

    private Status buildStatus(ContractUpdateDTO dto, Contract contract) {
        Status status = new Status();
        status.setTimestamp(LocalDateTime.now());
        status.setMotorcycle(contract.getMotorcycle());
        status.setDescription(dto.getStatus() != null ? dto.getStatus() : "Ativo");
        status.setContract(contract);
        return status;
    }
}
