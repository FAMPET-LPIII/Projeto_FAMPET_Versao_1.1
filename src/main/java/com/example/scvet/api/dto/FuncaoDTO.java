package com.example.scvet.api.dto;

import com.example.scvet.model.entity.Funcao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FuncaoDTO {

    private long idFuncao;
    private String descricao;

    public static FuncaoDTO create(Funcao funcao){
        ModelMapper modelMapper = new ModelMapper();
        FuncaoDTO dto = modelMapper.map(funcao, FuncaoDTO.class);
        return dto;
    }
}

