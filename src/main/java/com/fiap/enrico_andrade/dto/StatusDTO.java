package com.fiap.enrico_andrade.dto;

public class StatusDTO {
    private Integer id;
    private String description;

    public StatusDTO() {
    }

    public StatusDTO(String description) {
        this.description = description;
    }

    public StatusDTO(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
