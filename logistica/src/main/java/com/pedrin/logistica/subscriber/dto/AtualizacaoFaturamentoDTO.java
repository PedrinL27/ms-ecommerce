package com.pedrin.logistica.subscriber.dto;

import com.pedrin.logistica.model.StatusPedido;

public record AtualizacaoFaturamentoDTO(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal
) {
}
