package com.chun.samplemall.dao.impl;


import com.chun.samplemall.dao.ProductDao;
import com.chun.samplemall.dto.ProductQueryParams;
import com.chun.samplemall.dto.ProductRequest;
import com.chun.samplemall.model.Product;
import com.chun.samplemall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
       String sql ="SELECT product.product_id,product.product_name,product.category,product.image_url,product.price,product.stock,product.description,product.created_date,product.last_modified_date " +
               "FROM product WHERE 1=1 ";
        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql,map,productQueryParams);
        sql += " ORDER BY " + productQueryParams.getOderBy() + " " + productQueryParams.getSort();
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());
        return namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
    }

    @Override
    public Product getProducctById(Integer productId) {
        String sql ="SELECT product.product_id,product.product_name,product.category,product.image_url,product.price,product.stock,product.description,product.created_date,product.last_modified_date " +
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

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql ="INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES" +
                "(:productName,:category,:imageUrl,:price,:stock,:description,:createdDate,:lastModifiedDate)";

        Map<String,Object> map = new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
    String sql = "UPDATE product SET product_name=:productName,category=:category,image_url=:imageUrl,price=:price," +
            "stock=:stock,description=:description,last_modified_date=:lastModifiedDate WHERE product_id=:productId";
    HashMap<String,Object> map = new HashMap<>();
    map.put("productName",productRequest.getProductName());
    map.put("category",productRequest.getCategory().toString());
    map.put("imageUrl",productRequest.getImageUrl());
    map.put("price",productRequest.getPrice());
    map.put("stock",productRequest.getStock());
    map.put("description",productRequest.getDescription());
    map.put("lastModifiedDate",new Date());
    map.put("productId",productId);

    namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id= :productId";
        HashMap<String,Object> map = new HashMap<>();
        map.put("productId",productId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql ="SELECT COUNT(*) FROM product WHERE 1=1";
        Map<String, Object> map = new HashMap<>();
        sql = addFilteringSql(sql,map,productQueryParams);


        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    private String addFilteringSql(String sql,Map<String,Object> map,ProductQueryParams queryParams){
        if (queryParams.getCategory() != null){
            sql += " AND category=:category";
            map.put("category",queryParams.getCategory().name());
        }
        if (queryParams.getSearch() != null){
            sql += " AND product_name Like :search";
            map.put("search","%" + queryParams.getSearch() + "%");
        }
        return sql;
    }
}
