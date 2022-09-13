package com.raiseup.productservice.controller;

import com.raiseup.productservice.dto.ProductRequest;
import com.raiseup.productservice.dto.ProductResponse;
import com.raiseup.productservice.model.Product;
import com.raiseup.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse>getProducts(){
        return productService.getProducts();
    }

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
