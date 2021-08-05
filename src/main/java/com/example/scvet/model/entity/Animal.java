package com.example.scvet.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor



public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnimal;
    private String nome;
    private double peso;
    private String sexo;

    @ManyToOne
    private Especie especie;

    @ManyToOne
    private Cliente cliente;
    @JsonIgnore
    @OneToMany (mappedBy = "animal", cascade = CascadeType.ALL)
    private List<Consulta> consultas;

}
