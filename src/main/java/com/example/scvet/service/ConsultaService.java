package com.example.scvet.service;

import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.Consulta;
import com.example.scvet.model.repository.ConsultaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    private ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    public List<Consulta> getConsultas() {
        return repository.findAll();
    }

    public Optional<Consulta> getConsultaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Consulta salvar(Consulta consulta) {
        validar(consulta);
        return repository.save(consulta);
    }
    @Transactional
    public void excluir(Consulta consulta) {
        repository.delete(consulta);
    }

    public void validar(Consulta consulta) {
        if (consulta.getHoraIniciada() == null || consulta.getHoraIniciada().trim().equals("")) {
            throw new RegraNegocioException("Hora inicial invalida");
        }
        if (consulta.getHoraFinalizada() == null || consulta.getHoraFinalizada().trim().equals("")) {
            throw new RegraNegocioException("Hora de finalização invalida ");
        }
        if (consulta.getData() == null || consulta.getData().trim().equals("")) {
            throw new RegraNegocioException("Data invalida");
        }
        if (consulta.getDiagnostico() == null || consulta.getDiagnostico().trim().equals("")) {
            throw new RegraNegocioException("Diagnostico invalido");
        }

        if (consulta.getAnimal() == null || consulta.getAnimal().getIdAnimal() == null || consulta.getAnimal().getIdAnimal() == 0) {
            throw new RegraNegocioException("Animal invalido");
        }
        if (consulta.getMedico() == null || consulta.getMedico().getIdFuncionario() == null || consulta.getMedico().getIdFuncionario() == 0) {
            throw new RegraNegocioException("Medico invalido");
        }

    }
}
