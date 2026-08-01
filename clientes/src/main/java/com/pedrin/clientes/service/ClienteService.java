package com.pedrin.clientes.service;

import com.pedrin.clientes.model.Cliente;
import com.pedrin.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    public Optional<Cliente> obterPorCodigo(Long codigo){
        var clienteOptional = repository.findById(codigo);

        if (clienteOptional.isPresent()) {
            var cliente  = clienteOptional.get();
            if (!cliente.isAtivo()) {
                return Optional.empty();
            }
        }
        return clienteOptional;
    }

    public void deletar(Cliente cliente) {
        cliente.setAtivo(false);
        repository.save(cliente);
    }

    public Optional<Cliente> obterHistoricoPorCodigo(Long codigo) {
        return repository.findById(codigo);
    }
}
