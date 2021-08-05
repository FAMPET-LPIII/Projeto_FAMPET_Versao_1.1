package com.example.scvet.api.dto;

import com.example.scvet.model.entity.Consulta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ConsultaDTO {

    private Long idConsulta;
    private String data;
    private String horaIniciada;
    private String horaFinalizada;
    private String diagnostico;
    private Long idMedico;
    private Long idAnimal;

    public static ConsultaDTO create(Consulta consulta){

        ModelMapper modelMapper = new ModelMapper();
        ConsultaDTO dto =  modelMapper.map(consulta, ConsultaDTO.class);
        assert dto.getIdMedico().equals(consulta.getMedico().getIdFuncionario());
        assert dto.getIdAnimal().equals(consulta.getAnimal().getIdAnimal());

        return dto;
    }

}