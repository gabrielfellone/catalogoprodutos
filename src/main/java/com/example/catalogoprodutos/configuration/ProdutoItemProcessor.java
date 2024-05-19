package com.example.catalogoprodutos.configuration;

import com.example.catalogoprodutos.entity.Produto;
import com.example.catalogoprodutos.entity.dto.ProdutoDto;
import com.example.catalogoprodutos.interfaces.ProdutoMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProdutoItemProcessor implements ItemProcessor<ProdutoDto, Produto> {
    private final ProdutoMapper produtoMapper;

    public ProdutoItemProcessor(ProdutoMapper produtoMapper) {
        this.produtoMapper = produtoMapper;
    }

    @Override
    public Produto process(final ProdutoDto produtoDto) {
        return produtoMapper.toProduto(produtoDto);
    }
}