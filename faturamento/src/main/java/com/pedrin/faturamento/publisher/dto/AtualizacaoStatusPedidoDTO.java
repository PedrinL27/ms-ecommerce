package com.pedrin.faturamento.publisher.dto;

public record AtualizacaoStatusPedidoDTO(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal
) {
}
