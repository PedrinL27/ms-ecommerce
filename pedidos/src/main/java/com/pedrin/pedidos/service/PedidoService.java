package com.pedrin.pedidos.service;

import com.pedrin.pedidos.repository.ItemPedidoRepository;
import com.pedrin.pedidos.repository.PedidoRepository;
import com.pedrin.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    private final PedidoValidator pedidoValidator;
}
