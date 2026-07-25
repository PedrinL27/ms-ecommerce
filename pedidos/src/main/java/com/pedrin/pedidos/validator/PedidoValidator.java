package com.pedrin.pedidos.validator;

import com.pedrin.pedidos.client.ClienteClient;
import com.pedrin.pedidos.client.ProdutosClient;
import com.pedrin.pedidos.model.ItemPedido;
import com.pedrin.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClienteClient clienteClient;


    public void validar(Pedido pedido){
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigo){

    }

    private void validarItem(ItemPedido item) {

    }
}
