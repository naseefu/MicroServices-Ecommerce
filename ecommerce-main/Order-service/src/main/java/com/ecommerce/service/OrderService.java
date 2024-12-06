package com.ecommerce.service;

import com.ecommerce.DTO.CartDTO;
import com.ecommerce.DTO.Response;
import com.ecommerce.entity.OrderEntity;
import com.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private OrderRepository orderRepository;

    public Response orderItem(String userId){

        Response response = new Response();

        try{

            boolean flag = true;

            List<CartDTO> cartDTOS = webClient.get()
                    .uri("http://localhost:8083/cart/get/"+userId)
                    .retrieve().bodyToFlux(CartDTO.class).collectList().block();

            if(cartDTOS==null){
                System.out.println("cartDTOS is null");
            }

            if(cartDTOS!=null){

                System.out.println(cartDTOS.stream().map(CartDTO::getProductId));


            for(CartDTO cartDTO : cartDTOS){
                Boolean b = webClient.post().uri("http://localhost:8082/inventory/stock-exist")
                        .bodyValue(cartDTO).retrieve().bodyToMono(Boolean.class).block();
                System.out.println(b);
                if(Boolean.FALSE.equals(b)){
                    flag = false;
                    break;
                }
            }

            if(flag){
                response.setStatusCode(200);

                Boolean bool = webClient.post().uri("http://localhost:8082/inventory/order")
                        .bodyValue(cartDTOS).retrieve().bodyToMono(Boolean.class).block();

                if(Boolean.TRUE.equals(bool)){
                    for(CartDTO cartDTO : cartDTOS){

                    OrderEntity orderEntity = new OrderEntity();

                    orderEntity.setOrderId(UUID.randomUUID().toString());
                    orderEntity.setDate(LocalDate.now());
                    orderEntity.setPrice(123123);
                    orderEntity.setQuantity(cartDTO.getQuantity());
                    orderEntity.setUserId(userId);
                    orderEntity.setProductId(cartDTO.getProductId());

                    orderRepository.save(orderEntity);

                }
                response.setStatusCode(200);
                response.setMessage("Order placed successfully");
                }
                else{
                    response.setStatusCode(400);
                    response.setMessage("Order placed failed");
                }
            }
            else{
                response.setStatusCode(400);
                response.setMessage("Out of stock");
            }}
            else{
                response.setStatusCode(400);
                response.setMessage("Out of stock");
            }
            return response;
        }

        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Failed to order item");
            return response;
        }


    }

}
