package com.leiHealth.baymax.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Diagnosticos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diagnosticos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "conversacion_id", nullable = false)
    private Conversaciones conversacion;

    @Column(nullable = false)
    private boolean validadoPorMedico;
}
