package com.example.scvet.api.controller;

import com.example.scvet.api.dto.EspecialidadeDTO;
import com.example.scvet.api.dto.FuncaoDTO;
import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.Especialidade;
import com.example.scvet.model.entity.Funcao;
import com.example.scvet.service.EspecialidadeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/especialidades")
@RequiredArgsConstructor

public class EspecialidadeController {
    private final EspecialidadeService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Especialidade> especialidades = service.getEspecialidades();
        return ResponseEntity.ok(especialidades.stream().map(EspecialidadeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Especialidade> especialidade = service.getEspecialidadeById(id);
        if(!especialidade.isPresent()){
            return new ResponseEntity("Especialidade não encontrada.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(especialidade.map(EspecialidadeDTO::create));
    }

    @PostMapping
    public ResponseEntity post(EspecialidadeDTO dto){
        try {
            Especialidade especialidade = converter(dto);
            especialidade = service.salvar(especialidade);
            return new ResponseEntity(especialidade, HttpStatus.CREATED);
        }catch(RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Especialidade converter (EspecialidadeDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Especialidade especialidade = modelMapper.map(dto, Especialidade.class);
        return especialidade;
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, EspecialidadeDTO dto) {
        if (!service.getEspecialidadeById(id).isPresent()) {
            return new ResponseEntity("Especialidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Especialidade especialidade = converter(dto);
            especialidade.setIdEspecialidade(id);
            service.salvar(especialidade);
            return ResponseEntity.ok(especialidade);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Especialidade> especialidade = service.getEspecialidadeById(id);
        if (!especialidade.isPresent()) {
            return new ResponseEntity("Especialidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(especialidade.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
