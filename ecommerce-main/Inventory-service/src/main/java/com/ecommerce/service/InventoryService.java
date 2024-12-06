package com.ecommerce.service;

import com.ecommerce.DTO.CartDTO;
import com.ecommerce.DTO.Response;
import com.ecommerce.entity.Inventory;
import com.ecommerce.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Response saveInventory(Inventory inventory) {
        Response response = new Response();

        try{

            inventory.setUpdated(LocalDate.now());
            inventory.setReservedQuantity(0);

            inventoryRepository.save(inventory);

            response.setStatusCode(200);
            response.setMessage("Inventory saved");
            return response;

        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Failed");
            return response;
        }
    }

    public Response isStock(CartDTO cartDTO){

        Response response = new Response();

        try{

            Inventory inventory = inventoryRepository.findByProductId(cartDTO.getProductId());

            if(inventory.getQuantity()>=cartDTO.getQuantity()){
                response.setStatusCode(200);
                return response;
            }
            else{
                response.setStatusCode(400);
                return response;
            }

        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Failed");
            return response;
        }

    }

    public Response placeOrderFromInventory(List<CartDTO> cartDTOS){

        Response response = new Response();

        try {

            for(CartDTO cartDTO : cartDTOS){
                Inventory inventory = inventoryRepository.findByProductId(cartDTO.getProductId());
                if(inventory.getQuantity()>=cartDTO.getQuantity()){
                    inventory.setQuantity(inventory.getQuantity()-cartDTO.getQuantity());
                    inventory.setReservedQuantity(inventory.getReservedQuantity()+cartDTO.getQuantity());
                    inventoryRepository.save(inventory);
                }
            }
            response.setStatusCode(200);
            response.setMessage("Inventory placed");
            return response;

        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Failed");
            return response;
        }

    }

}
