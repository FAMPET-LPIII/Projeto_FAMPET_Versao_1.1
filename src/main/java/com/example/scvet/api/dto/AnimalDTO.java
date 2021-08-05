package com.example.scvet.api.dto;

import com.example.scvet.model.entity.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AnimalDTO {

    private Long idAnimal;
    private String nomeAnimal;
    private double peso;
    private String sexo;
    private Long idCliente;
    private Long idEspecie;


    public static AnimalDTO create(Animal animal){

        ModelMapper modelMapper = new ModelMapper();
        AnimalDTO dto = modelMapper.map(animal, AnimalDTO.class);
        assert dto.getIdCliente().equals(animal.getCliente().getIdCliente());
        assert dto.getIdEspecie().equals(animal.getEspecie().getIdEspecie());
        return dto;
    }

}
