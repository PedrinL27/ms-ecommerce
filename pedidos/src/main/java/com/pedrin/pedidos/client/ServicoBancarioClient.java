package com.pedrin.pedidos.client;

import com.pedrin.pedidos.exceptions.PagamentoNaoAprovadoException;
import com.pedrin.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class ServicoBancarioClient {

    Random random = new Random();

    public String solicitarPagamento(Pedido pedido){
        int chanceErro = random.nextInt(10);
        if (chanceErro == 1) {
            throw new PagamentoNaoAprovadoException("Pagamento nao aprovado favor verificar com o banco");
        }
        return UUID.randomUUID().toString();
    }
}
