package com.example.catalogoprodutos.integration;


import com.example.catalogoprodutos.entity.dto.PedidoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void errorProdutoFilaPedido(PedidoDto message) throws JsonProcessingException {
        amqpTemplate.convertAndSend(
                "pedido-response-erro-exchange",
                "pedido-response-erro-rout-key",
                objectMapper.writeValueAsString(message)
        );
    }

    public void validaCliente(PedidoDto pedidoDto) throws JsonProcessingException {
        amqpTemplate.convertAndSend(
                "pedido-cliente-exchange",
                "pedido-cliente-rout-key",
                objectMapper.writeValueAsString(pedidoDto)
        );
    }

}
