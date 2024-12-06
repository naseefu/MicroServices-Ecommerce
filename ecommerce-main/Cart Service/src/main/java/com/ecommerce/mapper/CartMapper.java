package com.ecommerce.mapper;

import com.ecommerce.DTO.CartDTO;
import com.ecommerce.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    List<CartDTO> toCartDTOList(List<Cart> cartList);

}
