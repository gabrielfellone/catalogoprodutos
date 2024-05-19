package com.example.catalogoprodutos.controller.resources;

import com.example.catalogoprodutos.entity.Produto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class ProdutoResponse {

    private String nome;
    private String descricao;
    private Long estoque;

    public ProdutoResponse(Optional<Produto> produto){
        this.nome = produto.get().getNome();
        this.descricao = produto.get().getDescricao();
        this.estoque = produto.get().getEstoque();
    }


}
