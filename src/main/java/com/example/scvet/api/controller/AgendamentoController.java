package com.example.scvet.api.controller;

import com.example.scvet.api.dto.AgendamentoDTO;
import com.example.scvet.api.dto.FuncaoDTO;
import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.Agendamento;
import com.example.scvet.model.entity.Cliente;
import com.example.scvet.model.entity.Funcao;
import com.example.scvet.model.entity.Funcionario;
import com.example.scvet.service.AgendamentoService;
import com.example.scvet.service.ClienteService;
import com.example.scvet.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/agendamentos")
@RequiredArgsConstructor

public class AgendamentoController {
    private final AgendamentoService service;
    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;

    @GetMapping()
    public ResponseEntity get(){
        List<Agendamento> agendamentos = service.getAgendamentos();
        return ResponseEntity.ok(agendamentos.stream().map(AgendamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Agendamento> agendamento = service.getAgendamentoById(id);
        if(!agendamento.isPresent()){
            return new ResponseEntity("Agendamento não encontrado.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(agendamento.map(AgendamentoDTO::create));
    }


    @PostMapping()
    public ResponseEntity post(AgendamentoDTO dto){
        try {
            Agendamento agendamento = converter(dto);
            agendamento = service.salvar(agendamento);
            return new ResponseEntity(agendamento, HttpStatus.CREATED);
        }catch(RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Agendamento converter (AgendamentoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Agendamento agendamento = modelMapper.map(dto, Agendamento.class);
        if(dto.getIdCliente() != null){
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if(!cliente.isPresent()){
                agendamento.setCliente(null);
            }else{
                agendamento.setCliente(cliente.get());
            }
        }

        if(dto.getIdFuncionario()!=null){
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if(!funcionario.isPresent()){
                agendamento.setFuncionario(null);
            }else{
                agendamento.setFuncionario(funcionario.get());
            }
        }
        return agendamento;
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, AgendamentoDTO dto) {
        if (!service.getAgendamentoById(id).isPresent()) {
            return new ResponseEntity("Agendamento não encontrado.", HttpStatus.NOT_FOUND);
        }
        try {
            Agendamento agendamento = converter(dto);
            agendamento.setIdAgendamento(id);
            service.salvar(agendamento);
            return ResponseEntity.ok(agendamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Agendamento> agendamento = service.getAgendamentoById(id);
        if (!agendamento.isPresent()) {
            return new ResponseEntity("Agendamento não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(agendamento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
