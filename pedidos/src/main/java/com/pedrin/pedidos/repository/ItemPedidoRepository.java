package com.pedrin.pedidos.repository;

import com.pedrin.pedidos.model.ItemPedido;
import com.pedrin.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido(Pedido pedido);
}
