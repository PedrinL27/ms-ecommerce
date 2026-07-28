package com.pedrin.pedidos.service;

import com.pedrin.pedidos.client.ServicoBancarioClient;
import com.pedrin.pedidos.exceptions.ItemNaoEncontradoException;
import com.pedrin.pedidos.model.DadosPagamento;
import com.pedrin.pedidos.model.Pedido;
import com.pedrin.pedidos.model.enums.StatusPedido;
import com.pedrin.pedidos.model.enums.TipoPagamento;
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

    public Pedido atualizarPedido(Pedido pedido) {
        realizarPersistencia(pedido);
        return pedido;
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigo, String dados, TipoPagamento tipoPagamento) {
        var pedidoEncontrado = pedidoRepository.findById(codigo);
        if (pedidoEncontrado.isEmpty()) {
            throw new ItemNaoEncontradoException("Pedido nao encontrado para o codigo informado");
        }
        Pedido pedido = pedidoEncontrado.get();

        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setDados(dados);
        dadosPagamento.setTipoPagamento(tipoPagamento);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Nova tentativa de pagamento, aguarde");
        pedido.setChavePagamento(servicoBancarioClient.solicitarChavePagamento());

        pedidoRepository.save(pedido);
    }

    private void enviarSolicitacaoPedido(Pedido pedido) {
        String chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);

    }
    private void realizarPersistencia(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }
}
