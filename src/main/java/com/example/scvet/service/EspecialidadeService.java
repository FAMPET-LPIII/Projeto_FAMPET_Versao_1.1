package com.example.scvet.service;

import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.Especialidade;
import com.example.scvet.model.entity.Funcao;
import com.example.scvet.model.repository.EspecialidadeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadeService {

    private EspecialidadeRepository repository;

    public EspecialidadeService(EspecialidadeRepository repository){
        this.repository = repository;
    }

    public List<Especialidade> getEspecialidades(){
        return repository.findAll();
    }

    public Optional<Especialidade> getEspecialidadeById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Especialidade salvar(Especialidade especialidade){
        validar(especialidade);
        return  repository.save(especialidade);
    }

    public void validar (Especialidade especialidade){
        if (especialidade.getDescricao() == null || especialidade.getDescricao().trim().equals("")){
            throw new RegraNegocioException("Descrição de especialidade inválida.");
        }
    }

    public void excluir(Especialidade especialidade) {
        repository.delete(especialidade);
    }
}
