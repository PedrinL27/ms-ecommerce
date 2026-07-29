package com.pedrin.faturamento.subscriber;

import com.pedrin.faturamento.model.Pedido;
import com.pedrin.faturamento.service.GeradorNotaFiscalService;
import com.pedrin.faturamento.subscriber.dto.DetalhePedidoDTO;
import com.pedrin.faturamento.subscriber.mapper.PedidoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoPagoSubscriber {

    private final ObjectMapper objectMapper;

    private final GeradorNotaFiscalService service;

    private final PedidoMapper pedidoMapper;

    @KafkaListener(groupId = "ecommerce-faturamento", topics = "${ecommerce.config.kafka.topics.pedidos-pagos}")
    public void listen(String json){
        try {
            log.info("Recebendo o pedido para faturamento: {}", json);
            var dto = objectMapper.readValue(json, DetalhePedidoDTO.class);
            Pedido pedido = pedidoMapper.map(dto);
            service.gerar(pedido);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
