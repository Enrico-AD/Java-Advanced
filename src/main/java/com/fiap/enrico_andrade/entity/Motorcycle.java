package com.fiap.enrico_andrade.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "moto")
public class Motorcycle {
    @Id
    @Column(name = "id_moto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "placa", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "chassi", nullable = false, unique = true)
    private String chassis;

    @Column(name = "num_motor")
    private String engineNumber;

    @ManyToOne
    @JoinColumn(name = "modelo_id_modelo")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "patio_id_patio")
    private Yard yard;

    @OneToMany(mappedBy = "motorcycle", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Status> statuses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Yard getYard() {
        return yard;
    }

    public void setYard(Yard yard) {
        this.yard = yard;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
