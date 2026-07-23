package com.pedrin.pedidos.controller;

import com.pedrin.pedidos.controller.dto.NovoPedidoDTO;
import com.pedrin.pedidos.controller.mappers.PedidoMapper;
import com.pedrin.pedidos.model.Pedido;
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
    private final PedidoMapper pedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO dto) {
        Pedido pedido = service.criarPedido(
                pedidoMapper.toEntity(dto));
        return ResponseEntity.ok(pedido.getCodigo());
    }
}
