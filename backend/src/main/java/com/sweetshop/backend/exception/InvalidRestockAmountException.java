package com.sweetshop.backend.exception;

public class InvalidRestockAmountException extends RuntimeException {
    public InvalidRestockAmountException() {
        super("Restock amount must be positive");
    }
}