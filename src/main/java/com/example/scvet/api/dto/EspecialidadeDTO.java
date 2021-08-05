package com.example.scvet.api.dto;

import com.example.scvet.model.entity.Especialidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EspecialidadeDTO {

    private long idEspecialidade;
    private String descricao;

    public static EspecialidadeDTO create(Especialidade especialidade){

        ModelMapper modelMapper = new ModelMapper();
        EspecialidadeDTO dto = modelMapper.map(especialidade, EspecialidadeDTO.class);
        return dto;
    }
}

