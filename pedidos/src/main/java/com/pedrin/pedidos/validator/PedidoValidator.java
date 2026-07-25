package com.pedrin.pedidos.validator;

import com.pedrin.pedidos.client.ClienteClient;
import com.pedrin.pedidos.client.ProdutosClient;
import com.pedrin.pedidos.exceptions.ValidationException;
import com.pedrin.pedidos.model.ItemPedido;
import com.pedrin.pedidos.model.Pedido;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClienteClient clienteClient;


    public void validar(Pedido pedido){
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigo){
        try {
            var response = clienteClient.obterDados(codigo);
            log.info("Cliente Encontrado: {}", response.getBody());
        } catch (FeignException.NotFound e) {
            var message = String.format("Cliente de codigo %d nao encontrado", codigo);
            throw new ValidationException("codigoCliente", message);
        }

    }

    private void validarItem(ItemPedido item) {
        Long codigo = item.getCodigoProduto();
        try {
            var response = produtosClient.obterDados(codigo);
            log.info("Produto Encontrado: {}", response.getBody());
        } catch (FeignException.NotFound e) {
            log.error("Produto nao encotrado");
            var message = String.format("Produto de codigo %d nao encontrado", codigo);
            throw new ValidationException("codigoProduto", message);
        }
    }
}
