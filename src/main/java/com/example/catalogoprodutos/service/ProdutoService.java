package com.example.catalogoprodutos.service;

import com.example.catalogoprodutos.controller.resources.ProdutoResponse;
import com.example.catalogoprodutos.entity.Produto;
import com.example.catalogoprodutos.entity.dto.PedidoDto;
import com.example.catalogoprodutos.exception.ProdutoException;
import com.example.catalogoprodutos.integration.ProdutoProducer;
import com.example.catalogoprodutos.repository.ProdutoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProdutoService {


    private final ProdutoRepository produtoRepository;

    @Autowired
    ProdutoProducer producer;

    public ProdutoResponse buscaProdutoPorId(Long id) {
        log.info("Buscando Produto por ID na Base");
        try {
            Optional<Produto> getProduto = produtoRepository.findById(id);
            if (getProduto.isPresent()) {
                return new ProdutoResponse(getProduto);
            }
        } catch (ProdutoException e) {
            throw new ProdutoException("Erro ao buscar no banco de dados o produto!");
        }
        return null;
    }

    public void validaProduto(String payload) throws JSONException,
            JsonProcessingException {

        JSONObject jsonObject = new JSONObject(payload);
        log.info("Json Message Produto: {} ", jsonObject);

        String id = jsonObject.getString("id");
        String idCliente = jsonObject.getString("idCliente");
        String idProduto = jsonObject.getString("idProduto");

        validaProdutoPorMessage(Long.parseLong(idProduto),
                PedidoDto.builder().
                        id(UUID.fromString(id))
                        .idCliente(UUID.fromString(idCliente))
                        .idProduto(Long.parseLong(idProduto)).build());

    }

    public void validaProdutoPorMessage(Long idProduto, PedidoDto message) throws JsonProcessingException {
        log.info("Validando Produto se Existe na Base");
        Optional<Produto> getProduto = produtoRepository.findById(idProduto);
        if (!getProduto.isPresent()) {
            log.info("Produto não encontrado, postando mensagem de erro na fila de pedido");
            producer.errorProdutoFilaPedido(message);
        } else if(getProduto.get().getEstoque() <= 0){
            log.info("Produto encontrado porem sem estoque, postando mensagem de erro na fila de pedido");
            producer.errorProdutoFilaPedido(message);
        } else {
            log.info("Produto Encontrado e com Estoque... Seguindo validacao cliente");
            producer.validaCliente(message);
        }

    }


}
