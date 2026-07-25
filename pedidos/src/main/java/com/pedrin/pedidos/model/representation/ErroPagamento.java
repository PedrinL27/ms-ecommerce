package com.pedrin.pedidos.model.representation;

import com.pedrin.pedidos.model.enums.StatusPedido;

public record ErroPagamento(
        String erro,
        StatusPedido status
) {
}
