package com.valkyrie.order_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.valkyrie.order_service.model.Product;

@FeignClient("PRODUCT-SERVICE")
public interface ProductFeignController {

    @GetMapping("/product/find-product-by-id")
    ResponseEntity<Product> productById(@RequestParam int id);

    @PostMapping("/product/update-product")
    ResponseEntity<String> updateProduct(@RequestParam int id, @RequestBody Product product);
}
