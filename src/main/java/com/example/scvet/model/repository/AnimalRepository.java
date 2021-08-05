package com.example.scvet.model.repository;

import com.example.scvet.model.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AnimalRepository  extends JpaRepository<Animal, Long>{

}
