package com.pedrin.faturamento.publisher;

import com.pedrin.faturamento.model.Pedido;
import com.pedrin.faturamento.publisher.dto.AtualizacaoStatusPedidoDTO;
import com.pedrin.faturamento.publisher.dto.StatusPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaturamentoPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ecommerce.config.kafka.topics.pedidos-faturados}")
    private String topic;

    public void publicar(Pedido pedido, String url) {
        var dto = new AtualizacaoStatusPedidoDTO(
                pedido.codigo(),
                StatusPedido.FATURADO,
                url
        );

        try {
            String json = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send(topic, "data", json);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
