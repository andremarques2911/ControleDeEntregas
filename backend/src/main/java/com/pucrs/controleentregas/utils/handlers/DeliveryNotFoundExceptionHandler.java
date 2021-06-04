package com.pucrs.controleentregas.utils.handlers;

import com.pucrs.controleentregas.utils.exceptions.DeliveryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class DeliveryNotFoundExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity handleException(Exception e) {
        return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
