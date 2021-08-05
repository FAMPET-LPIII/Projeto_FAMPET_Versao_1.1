package com.example.scvet.service;

import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.Animal;
import com.example.scvet.model.repository.AnimalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private AnimalRepository repository;

    public AnimalService(AnimalRepository repository){
        this.repository = repository;
    }

    public List<Animal> getAnimais(){
         return repository.findAll();
    }

    public Optional<Animal> getAnimalById(Long id){
     return  repository.findById(id);
    }

   @Transactional
    public Animal salvar(Animal animal){
        validar(animal);
        return repository.save(animal);
    }
    @Transactional
    public void excluir(Animal animal) {
        repository.delete(animal);
    }

    public void validar(Animal animal){
        if (animal.getNome() == null || animal.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome invalido");
        }
        if (animal.getSexo() == null || animal.getSexo().trim().equals("")){
            throw new RegraNegocioException("Sexo invalido");
        }
        if (animal.getPeso() == 0){
            throw new RegraNegocioException("Peso invalido");
        }
        if(animal.getEspecie() == null || animal.getEspecie().getIdEspecie()==null || animal.getEspecie().getIdEspecie() ==0){
            throw new RegraNegocioException("Especie invalida");
        }
        if (animal.getCliente() == null || animal.getCliente().getIdCliente()== null || animal.getCliente().getIdCliente()==0){
            throw new RegraNegocioException("Cliente invalido");
        }
    }
}
