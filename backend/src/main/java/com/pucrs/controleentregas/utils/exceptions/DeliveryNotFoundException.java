package com.pucrs.controleentregas.utils.exceptions;

public class DeliveryNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeliveryNotFoundException(String message) {
        super(message);
    }

    public DeliveryNotFoundException() {
        super("Entrega não encontrada.");
    }

}
