package com.example.scvet.service;

import com.example.scvet.api.dto.FuncionarioDTO;
import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.Agendamento;
import com.example.scvet.model.entity.Animal;
import com.example.scvet.model.entity.Especie;
import com.example.scvet.model.entity.Funcionario;
import com.example.scvet.model.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {
    private FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public List<Funcionario> getFuncionarios() {
        return repository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Funcionario salvar(Funcionario funcionario){
        validar(funcionario);
        return repository.save(funcionario);
    }
    @Transactional
    public void excluir(Funcionario funcionario) {
        for(Agendamento agendamento: funcionario.getAgendamentos()){
            agendamento.setFuncionario(null);
//           agendamentoService.salvar(agendamento);
        }
        repository.delete(funcionario);
    }

    public void validar(Funcionario funcionario){
        if (funcionario.getCpf()== null || funcionario.getCpf().trim().equals("")){
            throw new RegraNegocioException("CPF de funcionario invalido");
        }
        if (funcionario.getEspecialidade()== null || funcionario.getEspecialidade().getIdEspecialidade()==null || funcionario.getEspecialidade().getIdEspecialidade()==0){
            throw new RegraNegocioException("Id de especialidade de funcionario invalido");
        }
        if (funcionario.getFuncao()== null || funcionario.getFuncao().getIdFuncao()==null || funcionario.getFuncao().getIdFuncao()==0){
            throw new RegraNegocioException("Id de funcao de funcionario invalido");
        }
        if (funcionario.getNome()== null || funcionario.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome de funcionario invalido");
        }
        if (funcionario.getEmail()== null || funcionario.getEmail().trim().equals("")){
            throw new RegraNegocioException("Email de funcionario invalido");
        }
        if (funcionario.getLogradouro()== null || funcionario.getLogradouro().trim().equals("")){
            throw new RegraNegocioException("Logradouro de funcionario invalido");
        }
        if (funcionario.getNumero()== null || funcionario.getNumero().trim().equals("")){
            throw new RegraNegocioException("Numero de funcionario invalido");
        }
        if (funcionario.getComplemento()== null || funcionario.getComplemento().trim().equals("")){
            throw new RegraNegocioException("Complemento de funcionario invalido");
        }
        if (funcionario.getBairro()== null || funcionario.getBairro().trim().equals("")){
            throw new RegraNegocioException("Bairro de funcionario invalido");
        }
        if (funcionario.getCidade()== null || funcionario.getCidade().trim().equals("")){
            throw new RegraNegocioException("Cidade de funcionario invalido");
        }
        if (funcionario.getUf()== null || funcionario.getUf().trim().equals("")){
            throw new RegraNegocioException("UF de funcionario invalido");
        }
        if (funcionario.getCep()== null || funcionario.getCep().trim().equals("")){
            throw new RegraNegocioException("Cep de funcionario invalido");
        }
        if (funcionario.getTelefone()== null || funcionario.getTelefone().trim().equals("")){
            throw new RegraNegocioException("Telefone de funcionario invalido");
        }
    }
}
