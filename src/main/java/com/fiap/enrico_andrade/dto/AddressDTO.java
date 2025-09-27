package com.fiap.enrico_andrade.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AddressDTO {
    @NotBlank(message = "Rua é obrigatória")
    private String streetName;

    @NotNull(message = "Número é obrigatório")
    @Min(value = 1, message = "Número deve ser >= 1")
    private Integer number;

    private String complement;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve ter 8 dígitos")
    private String zipCode;

    public AddressDTO() {
    }

    public AddressDTO(String streetName, Integer number, String complement, String zipCode) {
        this.streetName = streetName;
        this.number = number;
        this.complement = complement;
        this.zipCode = zipCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer  getNumber() {
        return number;
    }

    public void setNumber(Integer  number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
