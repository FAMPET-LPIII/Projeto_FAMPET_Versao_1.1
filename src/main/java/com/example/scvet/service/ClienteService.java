package com.example.scvet.service;


import com.example.scvet.api.dto.AnimalDTO;
import com.example.scvet.api.dto.ClienteDTO;
import com.example.scvet.exception.RegraNegocioException;
import com.example.scvet.model.entity.Agendamento;
import com.example.scvet.model.entity.Animal;
import com.example.scvet.model.entity.Cliente;
import com.example.scvet.model.entity.Especie;
import com.example.scvet.model.repository.ClienteRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private ClienteRepository repository;

    private AgendamentoService agendamentoService;

    public ClienteService(ClienteRepository repository){ this.repository = repository; }

    public List<Cliente> getClientes(){ return repository.findAll(); }

    public Optional<Cliente> getClienteById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Cliente salvar(Cliente cliente){
        validar(cliente);
        return repository.save(cliente);
    }

    @Transactional
    public void excluir(Cliente cliente) {
        Objects.requireNonNull(cliente.getIdCliente());
        for(Agendamento agendamento: cliente.getAgendamentos()){
            agendamento.setCliente(null);
//           agendamentoService.salvar(agendamento);
        }
        repository.delete(cliente); }

    public void validar(Cliente cliente){
        if (cliente.getNome() == null || cliente.getNome().trim().equals("")){
            throw new RegraNegocioException("Nome de cliente invalido");
        }
        if (cliente.getEmail()== null || cliente.getEmail().trim().equals("")){
            throw new RegraNegocioException("Email de cliente invalido");
        }
        if (cliente.getLogradouro()== null || cliente.getLogradouro().trim().equals("")){
            throw new RegraNegocioException("Logradouro de cliente invalido");
        }
        if (cliente.getNumero()== null || cliente.getNumero().trim().equals("")){
            throw new RegraNegocioException("Numero de cliente invalido");
        }
        if (cliente.getBairro()== null || cliente.getBairro().trim().equals("")){
            throw new RegraNegocioException("Bairro de cliente invalido");
        }
        if (cliente.getCidade()== null || cliente.getCidade().trim().equals("")){
            throw new RegraNegocioException("Cidade de cliente invalido");
        }
        if (cliente.getUf()== null || cliente.getUf().trim().equals("")){
            throw new RegraNegocioException("UF de cliente invalido");
        }
        if (cliente.getCep()== null || cliente.getCep().trim().equals("")){
            throw new RegraNegocioException("Cep de cliente invalido");
        }
        if (cliente.getTelefone()== null || cliente.getTelefone().trim().equals("")){
            throw new RegraNegocioException("Telefone de cliente invalido");
        }

    }
}
