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

public class Funcionario extends Pessoa{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFuncionario;
    private String cpf;

    @ManyToOne
    private Especialidade especialidade;
    @ManyToOne
    private Funcao funcao;

    @JsonIgnore
    @OneToMany (mappedBy = "funcionario")
    private List<Agendamento> agendamentos;
}
