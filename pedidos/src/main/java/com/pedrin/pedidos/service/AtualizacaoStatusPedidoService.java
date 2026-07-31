package com.pedrin.pedidos.service;

import com.pedrin.pedidos.model.enums.StatusPedido;
import com.pedrin.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoService {

    private final PedidoRepository repository;

    public void atualizarStatus(
            Long codigo, StatusPedido status, String urlNotaFiscal, String codigoRastreio
    ) {
        log.info("Recebido o pedido n-{} para atualizar o status", codigo);
    }
}
