package com.example.scvet.api.controller;

import com.example.scvet.api.dto.EspecialidadeDTO;
import com.example.scvet.api.dto.FuncaoDTO;
import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.Especialidade;
import com.example.scvet.model.entity.Funcao;
import com.example.scvet.service.FuncaoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/funcoes")
@RequiredArgsConstructor

public class FuncaoController {
    private final FuncaoService service;

    @ApiOperation("Obter funcoes cadastradas.")
    @GetMapping()
    public ResponseEntity get(){
        List<Funcao> funcoes = service.getFuncoes();
        return ResponseEntity.ok(funcoes.stream().map(FuncaoDTO::create).collect(Collectors.toList()));
    }

    @ApiOperation("Obter uma funcao cadastrada.")
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Funcao> funcao = service.getFuncaoById(id);
        if (!funcao.isPresent()){
            return new ResponseEntity("Função não encontrada.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funcao.map(FuncaoDTO::create));
    }

    @ApiOperation("Salva uma funcao.")
    @PostMapping
    public ResponseEntity post(FuncaoDTO dto){
        try {
            Funcao funcao = converter(dto);
            funcao = service.salvar(funcao);
            return new ResponseEntity(funcao, HttpStatus.CREATED);
        }catch(RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Funcao converter (FuncaoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Funcao funcao = modelMapper.map(dto, Funcao.class);
        return funcao;
    }

    @ApiOperation("Atualiza uma funcao.")
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, FuncaoDTO dto) {
        if (!service.getFuncaoById(id).isPresent()) {
            return new ResponseEntity("Função não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Funcao funcao = converter(dto);
            funcao.setIdFuncao(id);
            service.salvar(funcao);
            return ResponseEntity.ok(funcao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Apaga uma funcao.")
    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Funcao> funcao = service.getFuncaoById(id);
        if (!funcao.isPresent()) {
            return new ResponseEntity("Funcão não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(funcao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
