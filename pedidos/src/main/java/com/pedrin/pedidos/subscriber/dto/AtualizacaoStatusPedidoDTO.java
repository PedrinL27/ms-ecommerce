package com.pedrin.pedidos.subscriber.dto;

import com.pedrin.pedidos.model.enums.StatusPedido;

public record AtualizacaoStatusPedidoDTO(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal,
        String codigoRastreio
) {
}
