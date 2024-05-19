package com.example.catalogoprodutos.configuration;

import com.example.catalogoprodutos.entity.dto.ProdutoDto;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;


public class ProdutoFieldSetMapper implements FieldSetMapper<ProdutoDto> {
    @Override
    public ProdutoDto mapFieldSet(final FieldSet fieldSet) throws BindException {
        return ProdutoDto.builder()
                .id(fieldSet.readLong("id"))
                .nome(fieldSet.readString("nome"))
                .descricao(fieldSet.readString("descricao"))
                .estoque(fieldSet.readLong("estoque"))
                .build();
    }
}