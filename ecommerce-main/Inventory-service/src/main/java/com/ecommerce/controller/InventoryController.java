package com.ecommerce.controller;

import com.ecommerce.DTO.CartDTO;
import com.ecommerce.DTO.Response;
import com.ecommerce.entity.Inventory;
import com.ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public Boolean saveInventory(@RequestBody Inventory inventory) {

        Response response = inventoryService.saveInventory(inventory);
        return response.getStatusCode()==200;

    }

    @PostMapping("/stock-exist")
    public Boolean stockExist(@RequestBody CartDTO cartDTO) {

        Response response = inventoryService.isStock(cartDTO);
        return response.getStatusCode()==200;

    }

    @PostMapping("/order")
    public Boolean order(@RequestBody List<CartDTO> cartDTOList) {

        Response response = inventoryService.placeOrderFromInventory(cartDTOList);
        return response.getStatusCode()==200;

    }

}
