package com.raiseup.productservice.controller;

import com.raiseup.productservice.dto.ProductRequest;
import com.raiseup.productservice.dto.ProductResponse;
import com.raiseup.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping("/{productId}")
    public ProductResponse getProduct(@PathVariable("productId")String productId){
        return productService.getProductById(productId);
    }
}
