package com.pedrin.pedidos.service;

import com.pedrin.pedidos.client.ClienteClient;
import com.pedrin.pedidos.client.ProdutosClient;
import com.pedrin.pedidos.model.ItemPedido;
import com.pedrin.pedidos.model.Pedido;
import com.pedrin.pedidos.model.enums.StatusPedido;
import com.pedrin.pedidos.publisher.PagamentoPublisher;
import com.pedrin.pedidos.repository.ItemPedidoRepository;
import com.pedrin.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    private final ClienteClient clienteClient;
    private final ProdutosClient produtosClient;

    private final PagamentoPublisher publisher;

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
            prepararEPublicarPedidoPago(pedido);
            pedidoRepository.save(pedido);
        }
    }

    public Optional<Pedido> carregarDadosCompletosPedidos(Long codigo) {
        Optional<Pedido> pedido = pedidoRepository.findById(codigo);

        pedido.ifPresent(this::carregarDadosCliente);
        pedido.ifPresent(this::carregarDadosItensPedido);

        return pedido;
    }

    private void prepararEPublicarPedidoPago(Pedido pedido) {
        pedido.setStatus(StatusPedido.PAGO);

        carregarDadosCliente(pedido);
        carregarDadosItensPedido(pedido);

        publisher.publicar(pedido);
    }

    private void carregarDadosCliente(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        var response = clienteClient.obterDados(codigoCliente);
        pedido.setDadosCliente(response.getBody());

    }

    private void carregarDadosItensPedido(Pedido pedido) {
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itens);
        pedido.getItens().forEach(this::carregarDadosProduto);
    }

    private void carregarDadosProduto(ItemPedido item) {
        Long codigoProduto = item.getCodigoProduto();
        var response = produtosClient.obterDados(codigoProduto);

        item.setNome(response.getBody().nome());
    }
}
