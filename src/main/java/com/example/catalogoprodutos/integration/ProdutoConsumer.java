package com.example.catalogoprodutos.integration;

import com.example.catalogoprodutos.service.ProdutoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class ProdutoConsumer {

    @Autowired
    private ProdutoService produtoService;

    @RabbitListener(queues = {"pedido-produto-queue"})
    public void receive(@Payload Message message) throws JSONException, JsonProcessingException {
        String payload = String.valueOf(message.getPayload());
        produtoService.validaProduto(payload);
    }
}