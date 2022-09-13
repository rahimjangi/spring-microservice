package com.raiseup.productservice.mapper;

import com.raiseup.productservice.dto.ProductRequest;
import com.raiseup.productservice.dto.ProductResponse;
import com.raiseup.productservice.model.Product;

//@Mapper
public interface ProductMapper {
    Product product(ProductRequest productRequest);
    ProductRequest productRequest(Product product);

    ProductResponse productResponse(Product product);
}
