package com.raiseup.productservice.service;

import com.raiseup.productservice.dto.ProductRequest;
import com.raiseup.productservice.dto.ProductResponse;
import com.raiseup.productservice.model.Product;
import com.raiseup.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private  ModelMapper modelMapper= new ModelMapper();
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product =  new Product();
        modelMapper.map(productRequest,product);
        Product savedProduct = productRepository.save(product);
        log.info("Product saved: {}",savedProduct);
    }

    public ProductResponse getProductById(String productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty())throw new IllegalStateException("Product does not exist");
        ProductResponse productResponse= new ProductResponse();
        modelMapper.map(optionalProduct.get(),productResponse);
        return productResponse;
    }

    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = products.stream().map(product -> {
                    ProductResponse productResponse = new ProductResponse();
                    modelMapper.map(product, productResponse);
                    return productResponse;
                })
                .toList();
        return productResponses;
    }
}
