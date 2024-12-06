package com.ecommerce.controller;

import com.ecommerce.DTO.Response;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/place-order/{id}")
    public String placeOrder(@PathVariable String id) {
        System.out.println(id);
        Response response = orderService.orderItem(id);
        return response.getMessage();

    }

}
