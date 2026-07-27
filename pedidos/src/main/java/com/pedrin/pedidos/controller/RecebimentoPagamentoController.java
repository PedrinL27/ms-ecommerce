package com.pedrin.pedidos.controller;

import com.pedrin.pedidos.controller.dto.RecebimentoCallbackPagamentoDTO;
import com.pedrin.pedidos.service.CallbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
@RequiredArgsConstructor
public class RecebimentoPagamentoController {

    private final CallbackService service;

    @PostMapping
    public ResponseEntity<Object> atualizarStatusPagamento(
            @RequestBody RecebimentoCallbackPagamentoDTO body,
            @RequestHeader(name = "apiKey") String apiKey
            ) {
        service.atualizarStatusPagamenmto(
                body.codigo(),
                body.chavePagamento(),
                body.status(),
                body.observacoes());

        return ResponseEntity.ok().build();
    }
}