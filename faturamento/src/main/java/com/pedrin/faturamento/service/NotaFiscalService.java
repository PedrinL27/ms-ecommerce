package com.pedrin.faturamento.service;

import com.pedrin.faturamento.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class NotaFiscalService {

    @Value("classpath:reports/nota-fiscal.jrxml")
    private Resource notaFiscal;

    @Value("classpath:reports/logo.png")
    private Resource logo;

    public byte[] gerarNota(Pedido pedido) {
        try (var inputStream = notaFiscal.getInputStream()){

            Map<String, Object> params = getPedidoParams(pedido);

            var dataSource = new JRBeanCollectionDataSource(pedido.itens());

            JasperReport report = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private @NonNull Map<String, Object> getPedidoParams(Pedido pedido) {
        Map<String, Object> params = new HashMap<>();
        params.put("NOME", pedido.cliente().nome());
        params.put("CPF", pedido.cliente().cpf());
        params.put("LOGRADOURO", pedido.cliente().logradouro());
        params.put("BAIRRO", pedido.cliente().bairro());
        params.put("NUMERO", pedido.cliente().numero());
        params.put("TELEFONE", pedido.cliente().telefone());
        params.put("EMAIL", pedido.cliente().email());
        params.put("DATA_PEDIDO", pedido.data());
        params.put("TOTAL_PEDIDO", pedido.total());
        try {
            params.put("LOGO", logo.getFile().getAbsolutePath());
        } catch (IOException e) {
            log.error("Erro ao achar a logo: {}", e.getMessage());
        }
        return params;
    }
}
