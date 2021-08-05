package com.example.scvet.service;

import com.example.scvet.api.dto.AgendamentoDTO;
import com.example.scvet.api.dto.FuncaoDTO;
import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.Agendamento;
import com.example.scvet.model.entity.Especialidade;
import com.example.scvet.model.entity.Funcao;
import com.example.scvet.model.repository.FuncaoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncaoService {
    private FuncaoRepository repository;

    public FuncaoService(FuncaoRepository repository){
        this.repository = repository;
    }

    public List<Funcao> getFuncoes(){
        return repository.findAll();
    }

    public Optional<Funcao> getFuncaoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Funcao salvar(Funcao funcao){
        validar(funcao);
        return  repository.save(funcao);
    }

    public void validar (Funcao funcao){
        if (funcao.getDescricao() == null || funcao.getDescricao().trim().equals("")){
            throw new RegraNegocioException("Descrição de função inválida.");
        }
    }

    public void excluir(Funcao funcao) {
        repository.delete(funcao);
    }
}
