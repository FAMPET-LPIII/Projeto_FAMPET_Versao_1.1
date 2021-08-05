package com.example.scvet.service;

import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.Agendamento;
import com.example.scvet.model.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    private AgendamentoRepository repository;

    public AgendamentoService(AgendamentoRepository repository){
        this.repository = repository;
    }

    public List<Agendamento> getAgendamentos(){
        return repository.findAll();
    }

    public Optional<Agendamento> getAgendamentoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Agendamento salvar(Agendamento agendamento){
        validar(agendamento);
        return  repository.save(agendamento);
    }

    @Transactional
    public void excluir(Agendamento agendamento) {
        repository.delete(agendamento);
    }

    public void validar (Agendamento agendamento){
        if (agendamento.getData() == null || agendamento.getData().trim().equals("")){
            throw new RegraNegocioException("Data Inválida.");
        }
        if (agendamento.getHora() == null || agendamento.getHora().trim().equals("")){
            throw new RegraNegocioException("Hora Inválida.");
        }
        if (agendamento.getSituacao() == null || agendamento.getSituacao().trim().equals("")){
            throw new RegraNegocioException("Situação Inválida");
        }
        if (agendamento.getFuncionario() == null || agendamento.getFuncionario().getIdFuncionario() == null || agendamento.getFuncionario().getIdFuncionario() == 0){
            throw new RegraNegocioException("Funcionario Inválido.");
        }
        if (agendamento.getCliente() == null || agendamento.getCliente().getIdCliente() == null || agendamento.getCliente().getIdCliente() == 0){
            throw new RegraNegocioException("Cliente Inválido.");
        }
    }

}
