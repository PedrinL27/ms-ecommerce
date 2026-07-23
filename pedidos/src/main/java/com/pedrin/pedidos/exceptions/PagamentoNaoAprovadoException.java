package com.pedrin.pedidos.exceptions;

public class PagamentoNaoAprovadoException extends RuntimeException {
    public PagamentoNaoAprovadoException(String message) {
        super(message);
    }
}
