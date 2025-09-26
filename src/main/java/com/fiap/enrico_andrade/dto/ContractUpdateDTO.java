package com.fiap.enrico_andrade.dto;

import java.time.LocalDate;

public class ContractUpdateDTO {

    private Integer id;
    private String contractNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Integer motorcycleId;
    private MotorcycleDTO motorcycle;
    private TenantDTO tenant;

    public ContractUpdateDTO() {}

    public ContractUpdateDTO(
            Integer id,
            String contractNumber,
            LocalDate startDate,
            LocalDate endDate,
            String status,
            MotorcycleDTO motorcycle,
            TenantDTO tenant
    ) {
        this.id = id;
        this.contractNumber = contractNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.motorcycle = motorcycle;
        this.tenant = tenant;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public MotorcycleDTO getMotorcycle() {
        if (motorcycle == null) {
            motorcycle = new MotorcycleDTO();
        }
        return motorcycle;
    }

    public void setMotorcycle(MotorcycleDTO motorcycle) {
        this.motorcycle = motorcycle;
    }

    public TenantDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantDTO tenant) {
        this.tenant = tenant;
    }

    public Integer getMotorcycleId() {
        return motorcycleId;
    }

    public void setMotorcycleId(Integer motorcycleId) {
        this.motorcycleId = motorcycleId;
    }
}
