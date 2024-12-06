package com.ecommerce.service;

import com.ecommerce.DTO.CartDTO;
import com.ecommerce.DTO.Response;
import com.ecommerce.entity.Cart;
import com.ecommerce.mapper.CartMapper;
import com.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private WebClient webClient;

    public Response saveCart(Cart cart) {

        Response response = new Response();

        try{

            Boolean b = webClient.get().uri("http://localhost:8081/product/exist/"+cart.getProductId())
                            .retrieve().bodyToMono(Boolean.class).block();

            if(Boolean.TRUE.equals(b)){
                cart.setCreatedAt(LocalDate.now());
                cartRepository.save(cart);
                response.setStatusCode(200);
                response.setMessage("Cart saved");
            }
            else{
                response.setStatusCode(400);
                response.setMessage("Cart not saved");
            }

            return response;

        }
        catch (Exception e){
            response.setStatusCode(400);
            response.setMessage("Error while saving cart");
            return response;
        }


    }

    public Response getCartOfUser(String userId){

        Response response = new Response();

        try{

            List<Cart> carts = cartRepository.findByUserId(userId);
            List<CartDTO> cartDTOS = CartMapper.INSTANCE.toCartDTOList(carts);
            response.setCartDTOS(cartDTOS);
            response.setStatusCode(200);
            response.setMessage("Cart found");
            return response;

        }

        catch (Exception e){
            response.setStatusCode(400);
            response.setMessage("Error while getting cart of user");
            return response;
        }

    }

}
