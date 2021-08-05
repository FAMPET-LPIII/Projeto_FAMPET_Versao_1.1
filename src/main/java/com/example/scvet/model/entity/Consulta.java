package com.example.scvet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsulta;
    private String data;
    private String horaIniciada;
    private String horaFinalizada;
    private String diagnostico;
    @ManyToOne
    private Funcionario medico;
    @ManyToOne
    private Animal animal;

}
