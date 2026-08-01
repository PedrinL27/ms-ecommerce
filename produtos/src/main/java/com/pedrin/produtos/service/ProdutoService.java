package com.pedrin.produtos.service;

import com.pedrin.produtos.model.Produto;
import com.pedrin.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Optional<Produto> obterPorCodigo(Long codigo){
        var produtoOptional = repository.findById(codigo);

        if (produtoOptional.isPresent()) {
            var produto = produtoOptional.get();
            if (!produto.isAtivo()) {
                return Optional.empty();
            }
        }
        return produtoOptional;
    }

    public void deletar(Produto produto) {
        produto.setAtivo(false);
        repository.save(produto);
    }

    public Optional<Produto> obterHistoricoPorCodigo(Long codigo) {
        return repository.findById(codigo);
    }
}
