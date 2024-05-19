package com.example.catalogoprodutos.configuration;


import com.example.catalogoprodutos.entity.Produto;
import com.example.catalogoprodutos.repository.ProdutoRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ProdutoItemWriter implements ItemWriter<Produto> {

    private final ProdutoRepository produtoRepository;

    public ProdutoItemWriter(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void write(Chunk<? extends Produto> produtos) {
        produtos.getItems().forEach(produtoRepository::save);
    }
}