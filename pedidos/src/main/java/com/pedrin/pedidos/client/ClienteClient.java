package com.pedrin.pedidos.client;

import com.pedrin.pedidos.client.representation.ClienteRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes", url = "${ecommerce.pedidos.clients.clientes.url}")
public interface ClienteClient {

    @GetMapping("/{codigo}")
    ResponseEntity<ClienteRepresentation> obterDados(@PathVariable("codigo") Long codigo);
}