package com.pedrin.pedidos.controller.dto;

import com.pedrin.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO(
        String dados,
        TipoPagamento tipoPagamento
) {
}
