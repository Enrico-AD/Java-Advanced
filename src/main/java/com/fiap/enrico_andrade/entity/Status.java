package com.fiap.enrico_andrade.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Integer id;

    @Column(name = "data_hora_status", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "descricao", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "moto_id_moto")
    private Motorcycle motorcycle;

    @ManyToOne
    @JoinColumn(name = "contrato_id_contrato")
    private Contract contract;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Motorcycle getMotorcycle() {
        return motorcycle;
    }

    public void setMotorcycle(Motorcycle motorcycle) {
        this.motorcycle = motorcycle;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
