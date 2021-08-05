package com.example.scvet.api.controller;

import com.example.scvet.api.dto.*;
import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.Agendamento;
import com.example.scvet.model.entity.Animal;
import com.example.scvet.model.entity.Cliente;
import com.example.scvet.model.entity.Funcionario;
import com.example.scvet.service.AgendamentoService;
import com.example.scvet.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor

public class ClienteController {

    private final ClienteService service;
    private final AgendamentoService serviceAgendamento;

    @GetMapping()
    public ResponseEntity get() {
        List<Cliente> clientes = service.getClientes();
        return ResponseEntity.ok(clientes.stream().map(ClienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);

        if (!cliente.isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);

        }
        return ResponseEntity.ok(cliente.map(ClienteDTO::create));
    }

    @GetMapping("/{id}/animais")
    public ResponseEntity getAnimais(@PathVariable long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity("Cliente não encontrado.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente.get().getAnimais().stream().map(AnimalDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}/agendamentos")
    public ResponseEntity getAgendamentos(@PathVariable long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity("Agendamento não encontrado.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente.get().getAgendamentos().stream().map(AgendamentoDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(ClienteDTO dto) {
        try {
            Cliente cliente = converter(dto);
            cliente = service.salvar(cliente);
            return new ResponseEntity(cliente, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, ClienteDTO dto){
        if(!service.getClienteById(id).isPresent()){
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cliente cliente = converter(dto);
            cliente.setIdCliente(id);
            service.salvar(cliente);
            return ResponseEntity.ok(cliente);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cliente.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Cliente converter(ClienteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Cliente.class);
    }
}
