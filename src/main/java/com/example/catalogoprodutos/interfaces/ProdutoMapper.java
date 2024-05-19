package com.example.catalogoprodutos.interfaces;

import com.example.catalogoprodutos.entity.Produto;
import com.example.catalogoprodutos.entity.dto.ProdutoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toProduto(ProdutoDto produtoDto);
}