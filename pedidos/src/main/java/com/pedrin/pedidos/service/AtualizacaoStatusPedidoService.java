package com.pedrin.pedidos.service;

import com.pedrin.pedidos.model.enums.StatusPedido;
import com.pedrin.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoService {

    private final PedidoRepository repository;

    @Transactional
    public void atualizarStatus(
            Long codigo, StatusPedido status, String urlNotaFiscal, String codigoRastreio
    ) {
        log.info("Recebido o pedido n-{} para atualizar o status", codigo);

        repository.findById(codigo).ifPresent(pedido -> {
            pedido.setStatus(status);
            pedido.setUrlNotaFiscal(urlNotaFiscal);
            pedido.setCodigoRastreio(codigoRastreio);
        });

    }
}
