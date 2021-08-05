package com.example.scvet.model.repository;

import com.example.scvet.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository  extends JpaRepository<Cliente, Long>{
}
