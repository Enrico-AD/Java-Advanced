package com.fiap.enrico_andrade.dto;

import com.fiap.enrico_andrade.entity.Address;
import com.fiap.enrico_andrade.entity.Yard;

public class YardDTO {
    private Integer id;
    private String name;
    private String address;

    public YardDTO() {
    }

    public YardDTO(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public YardDTO(Yard entity) {
        this.id = entity.getId();
        this.name = entity.getName();

        if (entity.getAddress() != null) {
            Address a = entity.getAddress();
            this.address = String.format("%s, %s - %s",
                    a.getStreetName() != null ? a.getStreetName() : "",
                    a.getNumber() != null ? a.getNumber() : "",
                    a.getZipCode() != null ? a.getZipCode() : "");
        } else {
            this.address = null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
