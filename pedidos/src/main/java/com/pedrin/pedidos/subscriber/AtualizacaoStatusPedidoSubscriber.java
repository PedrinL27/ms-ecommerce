package com.pedrin.pedidos.subscriber;

import com.pedrin.pedidos.service.AtualizacaoStatusPedidoService;
import com.pedrin.pedidos.subscriber.dto.AtualizacaoStatusPedidoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoSubscriber {

    private final AtualizacaoStatusPedidoService service;
    private final ObjectMapper objectMapper;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",
                    topics = {"${ecommerce.config.kafka.topics.pedidos-faturados}",
                              "${ecommerce.config.kafka.topics.pedidos-enviados}"})
    public void receberAtualizacao(String json){
        log.info("Recebendo atualizacao: {}", json);

        try {
            var dto = objectMapper.readValue(json, AtualizacaoStatusPedidoDTO.class);
            service.atualizarStatus(dto.codigo(), dto.status(), dto.urlNotaFiscal(), dto.codigoRastreio());
        } catch (JacksonException e) {
            log.error("Erro ao receber pedido: {}, Erro: {}", json, e.getMessage());
        }
    }
}
