package com.pedrin.pedidos.service;

import com.pedrin.pedidos.model.Pedido;
import com.pedrin.pedidos.model.enums.StatusPedido;
import com.pedrin.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackService {

    private final PedidoRepository pedidoRepository;

    public void atualizarStatusPagamenmto(
            Long codigoPedido, String chavePagamento, Boolean status, String observacoes) {
        var pedidoEncontrado = pedidoRepository.findById(codigoPedido);

        if (pedidoEncontrado.isEmpty()) {
            var msg = String.format("Pedido nao encontrado para o codigo %d", codigoPedido);
            log.error(msg);
            return;
        }

        Pedido pedido = pedidoEncontrado.get();

        if (pedido.getStatus() == StatusPedido.ERRO_PAGAMENTO) {
            var msg = String.format("Pedido com erro de pagamento para o codigo %d", codigoPedido);
            log.error(msg);
        } else {
            pedido.setStatus(StatusPedido.PAGO);
            pedidoRepository.save(pedido);
        }
    }
}
