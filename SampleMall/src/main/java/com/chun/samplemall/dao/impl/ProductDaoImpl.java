package com.chun.samplemall.dao.impl;

import com.chun.samplemall.dao.ProductDao;
import com.chun.samplemall.model.Product;
import com.chun.samplemall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Product getProducctById(Integer productId) {
        String sql ="SELECT product.product_id,product.product_name,product.category,product.image_url,product.price,product.stock,product.description,product.create_date,product.last_modified_date " +
                "FROM product " +
                "WHERE product_id= :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId",productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (!productList.isEmpty()){
            return productList.get(0);
        }else{
            return null;
        }

    }
}
