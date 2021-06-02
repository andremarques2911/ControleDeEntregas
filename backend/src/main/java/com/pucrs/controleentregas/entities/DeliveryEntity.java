package com.pucrs.controleentregas.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deliveries")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private LocalDateTime registerDate;

    @Column
    private LocalDateTime withdrawalDate;

    @Column
    private String description;

    @Column
    private Integer apartment;

    @ManyToOne
    @JoinColumn(name = "operator")
    private OperatorEntity operator;

    @ManyToOne
    @JoinColumn(name = "resident")
    private ResidentEntity resident;

    @Override
    public String toString() {
        return "DeliveryEntity{" +
                "id=" + id +
                ", registerDate=" + registerDate +
                ", withdrawalDate=" + withdrawalDate +
                ", description='" + description + '\'' +
                ", apartment=" + apartment +
                '}';
    }
}
