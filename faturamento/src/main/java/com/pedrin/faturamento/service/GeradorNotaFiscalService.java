package com.pedrin.faturamento.service;

import com.pedrin.faturamento.bucket.BucketFile;
import com.pedrin.faturamento.bucket.BucketService;
import com.pedrin.faturamento.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class GeradorNotaFiscalService {

    private final NotaFiscalService notaFiscalService;
    private final BucketService bucketService;

    public void gerar(Pedido pedido){
        try {
            log.info("Gerando a nota fiscal para o pedido {}",pedido);

            byte[] byteArray = notaFiscalService.gerarNota(pedido);
            String nomeArquivo = String.format("notafiscal_pedido_%d.pdf", pedido.codigo());

            var file = new BucketFile(
                    nomeArquivo,
                    new ByteArrayInputStream(byteArray),
                    MediaType.APPLICATION_PDF,
                    byteArray.length);
            bucketService.upload(file);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
