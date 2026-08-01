package com.pedrin.logistica.publisher;

import com.pedrin.logistica.model.AtualizacaoEnvioPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnvioPedidoPublisher {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${ecommerce.config.kafka.topics.pedidos-enviados}")
    private String topico;

    public void enviar(AtualizacaoEnvioPedido atualizacaoEnvioPedido) {
        log.info("Enviando pedido {}", atualizacaoEnvioPedido.codigo());

        try {
            var json = objectMapper.writeValueAsString(atualizacaoEnvioPedido);
            kafkaTemplate.send(topico, "dados", json);
            log.info("Publicado o pedido {}, codigo de rastreio {}",
                    atualizacaoEnvioPedido.codigo(),
                    atualizacaoEnvioPedido.codigoRastreio());

        } catch (Exception e) {
            log.error("Erro ao publicar pedido",e);
        }
    }
}
