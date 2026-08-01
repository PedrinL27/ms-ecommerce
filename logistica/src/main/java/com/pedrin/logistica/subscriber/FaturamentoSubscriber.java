package com.pedrin.logistica.subscriber;

import com.pedrin.logistica.service.EnvioPedidoService;
import com.pedrin.logistica.subscriber.dto.AtualizacaoFaturamentoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaturamentoSubscriber {

    private final ObjectMapper objectMapper;
    private final EnvioPedidoService service;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
            topics = "${ecommerce.config.kafka.topics.pedidos-faturados}")
    public void listener(String json) {
        log.info("Recebendo a atualizacao do pedido: {}", json);

        try {
            var dto = objectMapper.readValue(json, AtualizacaoFaturamentoDTO.class);
            service.enviar(dto.codigo());
        } catch (JacksonException e) {
            log.error("Erro ao processar payload: {} | {}", json, e.getMessage());
        }
    }
}
