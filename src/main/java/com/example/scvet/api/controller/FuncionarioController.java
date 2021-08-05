package com.example.scvet.api.controller;

import com.example.scvet.api.dto.AgendamentoDTO;
import com.example.scvet.api.dto.FuncionarioDTO;
import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.*;
import com.example.scvet.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/funcionarios")
@RequiredArgsConstructor

public class FuncionarioController {

    private final FuncionarioService service;
    private final EspecialidadeService especialidadeService;
    private final FuncaoService funcaoService;
    private final AgendamentoService serviceAgendamento;


    @GetMapping()
    public ResponseEntity get(){
        List<Funcionario> funcionario = service.getFuncionarios();
        return ResponseEntity.ok(funcionario.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);

        if(!funcionario.isPresent()){
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(funcionario.map(FuncionarioDTO::create));
    }

    @GetMapping("/{id}/agendamentos")
    public ResponseEntity getAgendamentos(@PathVariable long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionario não encontrado.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funcionario.get().getAgendamentos().stream().map(AgendamentoDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(FuncionarioDTO dto){
        try {
            Funcionario funcionario = converter(dto);
            funcionario = service.salvar(funcionario);
            return new ResponseEntity(funcionario, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, FuncionarioDTO dto){
        if(!service.getFuncionarioById(id).isPresent()){
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Funcionario funcionario = converter(dto);
            funcionario.setIdFuncionario(id);
            service.salvar(funcionario);
            return ResponseEntity.ok(funcionario);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            for(Agendamento agendamento: funcionario.get().getAgendamentos()){
                agendamento.setFuncionario(null);
                serviceAgendamento.salvar(agendamento);
            }
            service.excluir(funcionario.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Funcionario converter(FuncionarioDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
        if (dto.getIdEspecialidade() != null){
            Optional<Especialidade> especialidade = especialidadeService.getEspecialidadeById(dto.getIdEspecialidade());
            if(!especialidade.isPresent()){
                funcionario.setEspecialidade(null);
            }else{
                funcionario.setEspecialidade(especialidade.get());

            }
        }

        if (dto.getIdFuncao() != null){
            Optional<Funcao> funcao = funcaoService.getFuncaoById(dto.getIdFuncao());
            if(!funcao.isPresent()){
                funcionario.setFuncao(null);
            }else{
                funcionario.setFuncao(funcao.get());

            }
        }
        return funcionario;
    }
}
