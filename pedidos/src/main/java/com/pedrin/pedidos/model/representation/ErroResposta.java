package com.pedrin.pedidos.model.representation;

public record ErroResposta(
        String mensagem,
        String campo,
        String erro
) {
}
