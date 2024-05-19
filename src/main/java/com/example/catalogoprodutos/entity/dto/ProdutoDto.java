package com.example.catalogoprodutos.entity.dto;

import lombok.Builder;



@Builder
public record ProdutoDto(
        Long id,
        String nome,
        String descricao,
        Long estoque) {
}