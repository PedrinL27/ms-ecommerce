package com.pedrin.faturamento.subscriber.dto;

import java.math.BigDecimal;
import java.util.List;

public record DetalhePedidoDTO(
        Long codigo,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone,
        String dataPedido,
        BigDecimal total,
        List<DetalheItemPedidoDTO> itens
) {
}
