package com.example.scvet.api.controller;

import com.example.scvet.api.dto.ConsultaDTO;
import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.*;
import com.example.scvet.service.AnimalService;
import com.example.scvet.service.ConsultaService;
import com.example.scvet.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/consultas")
@RequiredArgsConstructor

public class ConsultaController {
    private final ConsultaService service;
    private final FuncionarioService funcionarioService;
    private final AnimalService animalService;

    @GetMapping()
    public ResponseEntity get(){
        List<Consulta> consultas = service.getConsultas();
        return ResponseEntity.ok(consultas.stream().map(ConsultaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Consulta> consulta = service.getConsultaById(id);
        if(!consulta.isPresent()){
            return new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(consulta.map(ConsultaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(ConsultaDTO dto){
        try {
            Consulta consulta = converter(dto);
            consulta = service.salvar(consulta);
            return new ResponseEntity(consulta, HttpStatus.CREATED);


        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, ConsultaDTO dto) {
        if (!service.getConsultaById(id).isPresent()) {
            return new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Consulta consulta = converter(dto);
            consulta.setIdConsulta(id);
            service.salvar(consulta);
            return ResponseEntity.ok(consulta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Consulta> consulta = service.getConsultaById(id);
        if (!consulta.isPresent()) {
            return new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(consulta.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Consulta converter(ConsultaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Consulta consulta= modelMapper.map(dto, Consulta.class);
        if (dto.getIdMedico() != null){
            Optional<Funcionario>  funcionario = funcionarioService.getFuncionarioById(dto.getIdMedico());
            if(!funcionario.isPresent()){
                consulta.setMedico(null);
            }else{
                consulta.setMedico(funcionario.get());

            }
        }

        if (dto.getIdAnimal() != null){
            Optional<Animal> animal = animalService.getAnimalById(dto.getIdAnimal());
            if(!animal.isPresent()){
                consulta.setAnimal(null);
            }else{
                consulta.setAnimal(animal.get());

            }
        }

        return consulta;
    }
}