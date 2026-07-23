package com.pedrin.pedidos.controller;

import com.pedrin.pedidos.controller.dto.NovoPedidoDTO;
import com.pedrin.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody NovoPedidoDTO dto) {
        return ResponseEntity.ok().build();
    }
}
