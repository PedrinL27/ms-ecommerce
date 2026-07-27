package com.pedrin.pedidos.controller.dto;

import com.pedrin.pedidos.model.enums.TipoPagamento;

public record AdicionarNovoPagamentoDTO(
        Long codigoPedido,
        String dados,
        TipoPagamento tipoPagamento
) {
}
