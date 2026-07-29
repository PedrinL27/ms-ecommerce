package com.pedrin.faturamento.subscriber.mapper;

import com.pedrin.faturamento.model.Cliente;
import com.pedrin.faturamento.model.ItemPedido;
import com.pedrin.faturamento.model.Pedido;
import com.pedrin.faturamento.subscriber.dto.DetalheItemPedidoDTO;
import com.pedrin.faturamento.subscriber.dto.DetalhePedidoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido map(DetalhePedidoDTO dto){
        Cliente cliente = new Cliente(
                dto.nome(),
                dto.cpf(),
                dto.logradouro(),
                dto.numero(),
                dto.bairro(),
                dto.email(),
                dto.telefone()
        );
        List<ItemPedido> itens = dto.itens()
                .stream().map(this::mapItem).toList();

        return new Pedido(dto.codigo(), cliente, dto.dataPedido(), dto.total(), itens);
    }

    private ItemPedido mapItem(DetalheItemPedidoDTO dto) {
        return new ItemPedido(
                dto.codigoProduto(),
                dto.nome(),
                dto.valorUnitario(),
                dto.quantidade());
    }
}


