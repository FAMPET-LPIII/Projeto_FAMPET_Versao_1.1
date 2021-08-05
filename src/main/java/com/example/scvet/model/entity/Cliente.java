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

public class Cliente extends Pessoa{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    @JsonIgnore
    @OneToMany (mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Animal> animais;

    @JsonIgnore
    @OneToMany (mappedBy = "cliente")
    private List<Agendamento> agendamentos;
}
