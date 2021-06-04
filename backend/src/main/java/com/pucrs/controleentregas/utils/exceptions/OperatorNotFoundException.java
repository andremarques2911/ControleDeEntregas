package com.pucrs.controleentregas.utils.exceptions;

public class OperatorNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OperatorNotFoundException(String message) {
        super(message);
    }

    public OperatorNotFoundException() {
        super("Operador não encontrado.");
    }

}
