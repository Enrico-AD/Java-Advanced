package com.fiap.enrico_andrade.dto;

import com.fiap.enrico_andrade.entity.Motorcycle;

public class MotorcycleDTO {

    private Integer id;
    private String model;
    private String licensePlate;
    private String chassis;
    private YardDTO yard;
    private String status;

    public MotorcycleDTO() {
    }

    public MotorcycleDTO(
            Integer id,
            String model,
            String licensePlate,
            String chassis,
            YardDTO yard,
            String status
    ) {
        this.id = id;
        this.model = model;
        this.licensePlate = licensePlate;
        this.chassis = chassis;
        this.yard = yard;
        this.status = status;
    }

    public MotorcycleDTO(Integer id, String model, String licensePlate) {
        this.id = id;
        this.model = model;
        this.licensePlate = licensePlate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public YardDTO getYard() {
        return yard;
    }

    public void setYard(YardDTO yard) {
        this.yard = yard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }
}
