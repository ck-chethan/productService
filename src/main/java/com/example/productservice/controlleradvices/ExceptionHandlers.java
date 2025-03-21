package com.example.productservice.controlleradvices;

import com.example.productservice.dtos.ArithmeticExceptionDto;
import com.example.productservice.exceptions.ProductNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    // Add exception handlers here
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ArithmeticExceptionDto> handleArithmeticException() {
        ArithmeticExceptionDto arithmeticExceptionDto = new ArithmeticExceptionDto();
        arithmeticExceptionDto.setMessage("Arithmetic exception occurred");
        return new ResponseEntity<>(arithmeticExceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotExistsException.class)
    public ResponseEntity<ArithmeticExceptionDto> handleProductNotExistsException(ProductNotExistsException e) {
        ArithmeticExceptionDto arithmeticExceptionDto = new ArithmeticExceptionDto();
        arithmeticExceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(arithmeticExceptionDto, HttpStatus.NOT_FOUND);
    }
}
