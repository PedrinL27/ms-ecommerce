package com.pedrin.pedidos.controller;

import com.pedrin.pedidos.controller.dto.AdicionarNovoPagamentoDTO;
import com.pedrin.pedidos.controller.dto.NovoPedidoDTO;
import com.pedrin.pedidos.controller.mappers.PedidoMapper;
import com.pedrin.pedidos.exceptions.ItemNaoEncontradoException;
import com.pedrin.pedidos.exceptions.PagamentoNaoAprovadoException;
import com.pedrin.pedidos.exceptions.ValidationException;
import com.pedrin.pedidos.model.enums.StatusPedido;
import com.pedrin.pedidos.model.representation.ErroPagamento;
import com.pedrin.pedidos.model.representation.ErroResposta;
import com.pedrin.pedidos.model.Pedido;
import com.pedrin.pedidos.publisher.dto.DetalhePedidoDTO;
import com.pedrin.pedidos.publisher.mappers.DetalhePedidoMapper;
import com.pedrin.pedidos.service.CallbackService;
import com.pedrin.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final CallbackService callbackService;

    private final PedidoMapper pedidoMapper;
    private final DetalhePedidoMapper detalhePedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO dto) {
        try {
            Pedido pedido = service.criarPedido(pedidoMapper.toEntity(dto));
            return ResponseEntity.ok(pedido.getCodigo());
        } catch (ValidationException e) {
            var erro = new ErroResposta("Erro de validacao", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        } catch (PagamentoNaoAprovadoException e) {
            Pedido pedido = pedidoMapper.toEntity(dto);
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(e.getMessage());

            service.atualizarPedido(pedido);
            var erroPagamento = new ErroPagamento(pedido.getObservacoes(), pedido.getStatus());
            return ResponseEntity.ok(erroPagamento);
        }
    }

    @PostMapping("/pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento(@RequestBody AdicionarNovoPagamentoDTO dto) {
        try {
            service.adicionarNovoPagamento(
                    dto.codigoPedido(),
                    dto.dados(),
                    dto.tipoPagamento()
            );
        } catch (ItemNaoEncontradoException e) {
            ErroResposta resposta = new ErroResposta("Item nao encontrado", "codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(resposta);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{codigo}")
    public ResponseEntity<DetalhePedidoDTO> obterDetalhePedido(
            @PathVariable Long codigo
    ) {
        return callbackService
                .carregarDadosCompletosPedidos(codigo)
                .map(detalhePedidoMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
