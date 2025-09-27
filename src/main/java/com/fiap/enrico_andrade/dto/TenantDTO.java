package com.fiap.enrico_andrade.dto;

import com.fiap.enrico_andrade.util.CPF;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class TenantDTO {

    private Integer id;
    @NotBlank(message = "Nome é obrigatório")

    private String fullName;

    @CPF(message = "CPF inválido")
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @Valid
    private AddressDTO address;

    public TenantDTO() {
    }

    public TenantDTO(Integer id, String fullName, String cpf, AddressDTO address) {
        this.id = id;
        this.fullName = fullName;
        this.cpf = cpf;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
