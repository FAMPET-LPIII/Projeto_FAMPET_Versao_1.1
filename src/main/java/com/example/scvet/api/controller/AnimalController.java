package com.example.scvet.api.controller;

import com.example.scvet.api.dto.AnimalDTO;
import com.example.scvet.api.dto.ConsultaDTO;
import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.Animal;
import com.example.scvet.model.entity.Cliente;
import com.example.scvet.model.entity.Especie;
import com.example.scvet.service.AnimalService;
import com.example.scvet.service.ClienteService;
import io.swagger.annotations.*;
import com.example.scvet.service.EspecieService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/animais")
@RequiredArgsConstructor
@Api("API de Animais")

public class AnimalController {
    private final AnimalService service;
    private final EspecieService especieService;
    private final ClienteService clienteService;

    @ApiOperation("Obter animais cadastrados")
    @GetMapping()
    public ResponseEntity get(){
        List<Animal> animais = service.getAnimais();
        return ResponseEntity.ok(animais.stream().map(AnimalDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um animal")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Animal encontrado"),
            @ApiResponse(code = 404, message = "Animal não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do animal") Long id){
        Optional<Animal> animal = service.getAnimalById(id);
        if(!animal.isPresent()){
            return new ResponseEntity("Animal não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(animal.map(AnimalDTO::create));
    }
    @ApiOperation("Obter consultas de um animal")
    @GetMapping("/{id}/consultas")
    public ResponseEntity getListConsultas(@PathVariable("id") Long id){
        Optional<Animal> animal = service.getAnimalById(id);
        if(!animal.isPresent()){
            return new ResponseEntity("Animal não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(animal.get().getConsultas().stream().map(ConsultaDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    @ApiOperation("Salvar um novo animal")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Animal salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o animal")
    })
    public ResponseEntity post(AnimalDTO dto){
        try {
            Animal animal = converter(dto);
            animal = service.salvar(animal);

            return new ResponseEntity(animal, HttpStatus.CREATED);

        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @ApiOperation("Atualizar um animal")
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, AnimalDTO dto) {
        if (!service.getAnimalById(id).isPresent()) {
            return new ResponseEntity("Animal não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
           Animal animal = converter(dto);
            animal.setIdAnimal(id);
            service.salvar(animal);
            return ResponseEntity.ok(animal);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @ApiOperation("Apagar um animal")
    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Animal> animal = service.getAnimalById(id);
        if (!animal.isPresent()) {
            return new ResponseEntity("Animal não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(animal.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Animal converter(AnimalDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Animal animal = modelMapper.map(dto, Animal.class);
        if (dto.getIdEspecie() != null){
            Optional<Especie> especie = especieService.getEspecieById(dto.getIdEspecie());
            if(!especie.isPresent()){
                animal.setEspecie(null);
            }else{
                animal.setEspecie(especie.get());

            }
        }

        if (dto.getIdCliente() != null){
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if(!cliente.isPresent()){
                animal.setCliente(null);
            }else{
                animal.setCliente(cliente.get());

            }
        }

        return animal;
    }
}
