package com.example.scvet.api.dto;

import com.example.scvet.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FuncionarioDTO {

    private Long idFuncionario;
    private String cpf;
    private String nome;
    private String email;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String telefone;

    private Long idEspecialidade;
    private String descricaoEspecialidade;
    private Long idFuncao;
    private String descricaoFuncao;

    public static FuncionarioDTO create(Funcionario funcionario){
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(funcionario, FuncionarioDTO.class);
        assert dto.getIdEspecialidade().equals(funcionario.getEspecialidade().getIdEspecialidade());
        assert dto.getDescricaoEspecialidade().equals(funcionario.getEspecialidade().getDescricao());
        assert dto.getIdFuncao().equals(funcionario.getFuncao().getIdFuncao());
        assert dto.getDescricaoFuncao().equals(funcionario.getFuncao().getDescricao());

        return dto;
    }
}
