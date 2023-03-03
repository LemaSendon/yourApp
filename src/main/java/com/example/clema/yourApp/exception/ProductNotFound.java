package com.example.clema.yourApp.exception;

public class ProductNotFound extends Exception {

    public ProductNotFound(String productId) {
        super(String.format("Product %s not found", productId));
    }


}
