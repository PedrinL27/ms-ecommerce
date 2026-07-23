package com.pedrin.pedidos.service;

import com.pedrin.pedidos.client.ServicoBancarioClient;
import com.pedrin.pedidos.exceptions.PagamentoNaoAprovadoException;
import com.pedrin.pedidos.model.Pedido;
import com.pedrin.pedidos.model.enums.StatusPedido;
import com.pedrin.pedidos.repository.ItemPedidoRepository;
import com.pedrin.pedidos.repository.PedidoRepository;
import com.pedrin.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    private final PedidoValidator pedidoValidator;

    private final ServicoBancarioClient servicoBancarioClient;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        pedidoValidator.validar(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPedido(pedido);
        return pedido;
    }

    private void enviarSolicitacaoPedido(Pedido pedido) {
        try {
            String chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
            pedido.setChavePagamento(chavePagamento);
        } catch (PagamentoNaoAprovadoException e) {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes("Ocorreu um seguinte erro: " + e.getMessage());
        }
    }

    private void realizarPersistencia(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }
}
