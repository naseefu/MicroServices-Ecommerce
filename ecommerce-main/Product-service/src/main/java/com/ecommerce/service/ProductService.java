package com.ecommerce.service;

import com.ecommerce.DTO.InventoryDTO;
import com.ecommerce.DTO.Response;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WebClient webClient;

    @Transactional
    public Response saveProduct(Product product,int quantity){

        Response response = new Response();

        try{

            product.setModified(LocalDate.now());
            product.setActive(true);
            product.setCreated(LocalDate.now());

            Product product1 = productRepository.save(product);

            InventoryDTO inventoryDTO = new InventoryDTO();

            inventoryDTO.setProductId(product1.getId());
            inventoryDTO.setQuantity(quantity);

            Boolean b = webClient.post()
                            .uri("http://localhost:8082/inventory/save")
                                    .bodyValue(inventoryDTO)
                                            .retrieve().bodyToMono(Boolean.class).block();

            if(Boolean.TRUE.equals(b)){
                response.setStatusCode(200);
                response.setMessage("Product saved successfully");
            }
            else{
                response.setStatusCode(400);
                response.setMessage("Product not saved");
                productRepository.delete(product);
            }


            return response;

        }

        catch(Exception e){

            response.setStatusCode(500);
            response.setMessage("Failed "+e.getMessage());
            return  response;

        }

    }

    public Response productExist(String productId){

        Response response = new Response();

        try{

            if(productRepository.existsById(productId)){
                response.setStatusCode(200);
                response.setMessage("Product exists");
            }
            else{
                response.setStatusCode(400);
                response.setMessage("Product not exists");
            }
            return response;

        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Failed "+e.getMessage());
            return response;
        }

    }

}
