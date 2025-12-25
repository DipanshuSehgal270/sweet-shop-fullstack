package com.sweetshop.backend.exception;

public class SweetNotFoundException extends RuntimeException {
    public SweetNotFoundException(Long id) {
        super("Sweet not found with id: " + id);
    }
}