package com.chun.samplemall.service.impl;

import com.chun.samplemall.dao.ProductDao;
import com.chun.samplemall.model.Product;
import com.chun.samplemall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Override
    public Product getProducctById(Integer productId) {
        return productDao.getProducctById(productId);
    }
}
