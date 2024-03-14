package com.chun.samplemall.service.impl;

import com.chun.samplemall.constant.ProductCategory;
import com.chun.samplemall.dao.ProductDao;
import com.chun.samplemall.dto.ProductQueryParams;
import com.chun.samplemall.dto.ProductRequest;
import com.chun.samplemall.model.Product;
import com.chun.samplemall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Product getProducctById(Integer productId) {
        return productDao.getProducctById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProductById(Integer productId, ProductRequest productRequest) {
       productDao.updateProduct(productId,productRequest);

    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }

    @Override
    public Integer countProducts(ProductQueryParams productQueryParams) {
        return productDao.countProduct(productQueryParams);
    }
}
