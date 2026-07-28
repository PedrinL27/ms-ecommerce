package com.pedrin.pedidos.publisher;

import com.pedrin.pedidos.model.Pedido;
import com.pedrin.pedidos.publisher.mappers.DetalhePedidoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class PagamentoPublisher {

    private final DetalhePedidoMapper mapper;
    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${ecommerce.config.kafka.topics.pedidos-pagos}")
    private String topico;

    public void publicar(Pedido pedido) {
        log.info("Publicando pedido pago");

        try {
            var dto = mapper.toDTO(pedido);
            var json = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send(topico, "dados", json);
        } catch (JacksonException e) {
            log.error("Erro ao processar o JSON: {}", e.getMessage());
        } catch (RuntimeException e) {
            log.error("Erro tecnico: {}", e.getMessage());
        }
    }
}
