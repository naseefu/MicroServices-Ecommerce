package com.ecommerce.controller;

import com.ecommerce.DTO.CartDTO;
import com.ecommerce.DTO.Response;
import com.ecommerce.entity.Cart;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveCart(@RequestBody Cart cart){
        System.out.println(cart.getProductId());
        Response response = cartService.saveCart(cart);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<CartDTO> getCart(@PathVariable String id){

        Response response = cartService.getCartOfUser(id);
        return response.getCartDTOS();

    }

}
