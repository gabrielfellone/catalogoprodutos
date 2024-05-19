package com.example.catalogoprodutos.exception;

import java.io.Serial;

public class ProdutoException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;
    public ProdutoException(String message, Exception e) {super(message, e);}

    public ProdutoException(String message) {super(message);}
}
