package com.ecommerce.Controller;

import com.ecommerce.DTO.Response;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save/{quantity}")
    public ResponseEntity<Response> saveProduct(@RequestBody Product product,@PathVariable int quantity) {

        Response response = productService.saveProduct(product,quantity);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/exist/{id}")
    public Boolean existProduct(@PathVariable String id) {

        Response response = productService.productExist(id);
        return response.getStatusCode() == 200;

    }

}
