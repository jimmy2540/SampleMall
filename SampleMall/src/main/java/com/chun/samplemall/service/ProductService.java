package com.chun.samplemall.service;

import com.chun.samplemall.constant.ProductCategory;
import com.chun.samplemall.dto.ProductQueryParams;
import com.chun.samplemall.dto.ProductRequest;
import com.chun.samplemall.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProducctById(Integer productId);
    Integer createProduct (ProductRequest productRequest);

    void updateProductById(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
    Integer countProducts(ProductQueryParams productQueryParams);
}
