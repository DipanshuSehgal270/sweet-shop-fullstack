package com.sweetshop.backend.exception;

public class OutofStockException extends RuntimeException{

    public OutofStockException(String message)
    {
        super(message);
    }

}
