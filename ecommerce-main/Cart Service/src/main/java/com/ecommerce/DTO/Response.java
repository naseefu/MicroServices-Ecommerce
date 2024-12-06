package com.ecommerce.DTO;

import java.util.List;

public class Response {

    private String message;

    private int statusCode;

    private List<CartDTO> cartDTOS;

    public List<CartDTO> getCartDTOS() {
        return cartDTOS;
    }

    public void setCartDTOS(List<CartDTO> cartDTOS) {
        this.cartDTOS = cartDTOS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
