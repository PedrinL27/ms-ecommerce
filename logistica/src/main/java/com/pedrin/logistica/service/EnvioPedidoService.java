package com.pedrin.logistica.service;

import com.pedrin.logistica.model.AtualizacaoEnvioPedido;
import com.pedrin.logistica.model.StatusPedido;
import com.pedrin.logistica.publisher.EnvioPedidoPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnvioPedidoService {

    private final EnvioPedidoPublisher publisher;

    public void enviar(Long codigo) {
        var entidade = new AtualizacaoEnvioPedido(codigo, StatusPedido.ENVIADO, gerarCodigoRastreio());
        publisher.enviar(entidade);
    }

    private String gerarCodigoRastreio() {
        var random = new Random();

        char letra1 = (char) ('A' + random.nextInt(26));
        char letra2 = (char) ('A' + random.nextInt(26));
        int numeros = 100000000 + random.nextInt(899999999);
        return String.format("%c%c%dBR", letra1, letra2, numeros);
    }
}

