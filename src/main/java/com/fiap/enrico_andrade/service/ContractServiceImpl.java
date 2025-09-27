package com.fiap.enrico_andrade.service;

import com.fiap.enrico_andrade.dto.AddressDTO;
import com.fiap.enrico_andrade.dto.ContractDTO;
import com.fiap.enrico_andrade.dto.ContractUpdateDTO;
import com.fiap.enrico_andrade.dto.MotorcycleDTO;
import com.fiap.enrico_andrade.dto.StatusDTO;
import com.fiap.enrico_andrade.dto.TenantDTO;
import com.fiap.enrico_andrade.dto.YardDTO;
import com.fiap.enrico_andrade.entity.Address;
import com.fiap.enrico_andrade.entity.Contract;
import com.fiap.enrico_andrade.entity.Motorcycle;
import com.fiap.enrico_andrade.entity.Status;
import com.fiap.enrico_andrade.entity.Tenant;
import com.fiap.enrico_andrade.entity.Yard;
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
        return contractRepository.findById(id).map(contract -> {

            Status lastStatus = statusRepository.findLastStatusByContractId(contract.getId());

            Tenant tenant = contract.getTenant();
            AddressDTO addressDTO = null;
            TenantDTO tenantDTO = null;

            if (tenant != null) {
                Address address = tenant.getAddress();
                if (address != null) {
                    addressDTO = new AddressDTO(
                            address.getStreetName(),
                            Integer.parseInt(address.getNumber()),
                            address.getComplement(),
                            address.getZipCode()
                    );
                }

                tenantDTO = new TenantDTO(
                        tenant.getId(),
                        tenant.getFullName(),
                        tenant.getCpf(),
                        addressDTO
                );
            }

            StatusDTO statusDTO = new StatusDTO(
                    lastStatus.getId(),
                    lastStatus.getDescription()
            );

            MotorcycleDTO motorcycleDTO = null;
            if (contract.getMotorcycle() != null) {
                Motorcycle motorcycle = motorcycleRepository.findById(contract.getMotorcycle().getId())
                        .orElseThrow(() -> new RuntimeException("Motocicleta não encontrada"));

                Yard yard = motorcycle.getYard();
                YardDTO yardDTO = new YardDTO(
                        yard.getId(),
                        yard.getName(),
                        yard.getAddress().getStreetName()
                );


                motorcycleDTO = new MotorcycleDTO(
                        motorcycle.getId(),
                        motorcycle.getModel().getName(),
                        motorcycle.getLicensePlate(),
                        motorcycle.getChassis(),
                        motorcycle.getEngineNumber(),
                        yardDTO,
                        lastStatus.getDescription()
                );
            }

            return new ContractUpdateDTO(
                    contract.getId(),
                    contract.getContractNumber(),
                    contract.getStartDate(),
                    contract.getEndDate(),
                    statusDTO,
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

        Status lastStatus = statusRepository.findLastStatusByContractId(id);

        if (dto.getStatus() != null) {
            String newStatusDesc;

            if (dto.getStatus().getId() != null) {
                Status dbStatus = statusRepository.findById(dto.getStatus().getId())
                        .orElseThrow(() -> new RuntimeException("Status inválido"));
                newStatusDesc = dbStatus.getDescription();
            } else {
                newStatusDesc = dto.getStatus().getDescription();
            }

            if (lastStatus == null || !lastStatus.getDescription().equals(newStatusDesc)) {
                Status newStatus = new Status();
                newStatus.setDescription(newStatusDesc);
                newStatus.setTimestamp(LocalDateTime.now());
                newStatus.setContract(saved);
                newStatus.setMotorcycle(saved.getMotorcycle());
                statusRepository.save(newStatus);

                lastStatus = newStatus;
            }
        }

        return new ContractDTO(saved,
                lastStatus != null ? lastStatus.getDescription() : "Sem status");
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
        if (dto.getTenant() == null) {
            throw new RuntimeException("Locatário não informado");
        }

        TenantDTO tenantDTO = dto.getTenant();

        Tenant tenant = Optional.ofNullable(tenantDTO.getId())
                .flatMap(tenantRepository::findById)
                .orElse(new Tenant());

        tenant.setFullName(tenantDTO.getFullName());
        tenant.setCpf(tenantDTO.getCpf());

        if (tenantDTO.getAddress() != null) {
            Address updatedAddress = updateOrCreateAddress(tenant.getAddress(), tenantDTO.getAddress());
            tenant.setAddress(updatedAddress);
        }

        return tenantRepository.save(tenant);
    }

    private Address updateOrCreateAddress(Address current, AddressDTO dto) {
        Address address = current != null ? current : new Address();
        address.setStreetName(dto.getStreetName());
        address.setNumber(dto.getNumber().toString());
        address.setComplement(dto.getComplement());
        address.setZipCode(dto.getZipCode());
        return addressRepository.save(address);
    }

    private Motorcycle resolveMotorcycle(ContractUpdateDTO dto) {
        if (dto.getMotorcycle() == null) {
            throw new IllegalArgumentException("É obrigatório selecionar uma motocicleta");
        } else {
            return motorcycleRepository.findById(dto.getMotorcycle().getId())
                    .orElseThrow(() -> new RuntimeException("Motocicleta não encontrada"));
        }
    }

    private String generateContractNumber() {
        long count = contractRepository.count();
        String year = String.valueOf(LocalDate.now().getYear());
        String sequence = String.format("%03d", count + 1);
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
        address.setNumber(dto.getNumber().toString());
        address.setComplement(dto.getComplement());
        address.setZipCode(dto.getZipCode());
        return addressRepository.save(address);
    }

    private Status buildStatus(ContractUpdateDTO dto, Contract contract) {
        Status status = new Status();
        status.setTimestamp(LocalDateTime.now());
        status.setMotorcycle(contract.getMotorcycle());

        if (dto.getStatus() != null && dto.getStatus().getDescription() != null) {
            status.setDescription(dto.getStatus().getDescription());
        } else {
            status.setDescription("Ativo");
        }

        status.setContract(contract);
        return status;
    }

    private Status buildStatus(ContractUpdateDTO dto, Contract contract, Motorcycle motorcycle) {
        Status status = new Status();
        status.setTimestamp(LocalDateTime.now());
        status.setMotorcycle(motorcycle);

        if (dto.getStatus() != null && dto.getStatus().getDescription() != null) {
            status.setDescription(dto.getStatus().getDescription());
        } else {
            status.setDescription("Ativo");
        }

        status.setContract(contract);
        return status;
    }
}
