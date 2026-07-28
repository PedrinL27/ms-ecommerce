package com.pedrin.pedidos.publisher.dto;

import java.math.BigDecimal;

public record DetalheItemPedidoDTO(
        Long codigoProduto,
        String nome,
        Integer quantidade,
        BigDecimal valorUnitario
) {

    public BigDecimal getTotal(){
        return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
