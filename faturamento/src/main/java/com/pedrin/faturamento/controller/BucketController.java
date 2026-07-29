package com.pedrin.faturamento.controller;

import com.pedrin.faturamento.bucket.BucketFile;
import com.pedrin.faturamento.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService service;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try (InputStream is = file.getInputStream()){
            MediaType type = MediaType.parseMediaType(
                    Objects.requireNonNull(file.getContentType()));

            var bucketFile = new BucketFile(file.getOriginalFilename(), is, type,  file.getSize());
            service.upload(bucketFile);

            return ResponseEntity.ok("Arquivo enviado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao enviar o arquivo");
        }
    }

    @GetMapping
    public ResponseEntity<String> getUrl(@RequestParam String filename) {
        try {
            String url = service.getUrl(filename);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao criar url do arquivo");
        }
    }

    @GetMapping("/achou")
    public ResponseEntity<?> metodoSecreto(@RequestParam String param) {
        if (param == null) {
            return ResponseEntity.noContent().build();
        }
        if (param.isEmpty()) {
            return ResponseEntity.internalServerError().body("Metodo estranho? Comunicar com o Servidor? Como veio?");
        }
        return ResponseEntity.accepted().build();
    }
}