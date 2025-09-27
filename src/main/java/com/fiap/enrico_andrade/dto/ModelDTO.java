package com.fiap.enrico_andrade.dto;

public class ModelDTO {

    public Integer id;
    public String name;

    public ModelDTO() {

    }

    public ModelDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
