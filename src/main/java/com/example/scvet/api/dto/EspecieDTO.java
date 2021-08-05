package com.example.scvet.api.dto;

import com.example.scvet.model.entity.Especie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EspecieDTO {

    private Long idEspecie;
    private String nomeEspecie;

    public static EspecieDTO create(Especie especie){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(especie, EspecieDTO.class);
    }

}
