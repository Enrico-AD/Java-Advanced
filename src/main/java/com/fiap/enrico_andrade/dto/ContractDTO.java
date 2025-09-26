package com.fiap.enrico_andrade.dto;

import com.fiap.enrico_andrade.entity.Contract;
import com.fiap.enrico_andrade.entity.Motorcycle;
import com.fiap.enrico_andrade.entity.Tenant;

import java.time.LocalDate;

public class ContractDTO {
    private Integer id;
    private String contractNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private Motorcycle motorcycle;
    private Tenant tenant;
    private String status;

    public ContractDTO(Contract contract, String status) {
        this.id = contract.getId();
        this.contractNumber = contract.getContractNumber();
        this.startDate = contract.getStartDate();
        this.endDate = contract.getEndDate();
        this.motorcycle = contract.getMotorcycle();
        this.tenant = contract.getTenant();
        this.status = status;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Motorcycle getMotorcycle() {
        return motorcycle;
    }

    public void setMotorcycle(Motorcycle motorcycle) {
        this.motorcycle = motorcycle;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
