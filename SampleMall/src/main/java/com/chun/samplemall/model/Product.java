package com.chun.samplemall.model;

import com.chun.samplemall.constant.ProductCategory;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Product {
    private Integer productId;
    private String productName;
    private ProductCategory category;
    private  String imageUrl;
    private  Integer price;
    private  Integer stock;
    private  String description;
    private  Timestamp createdDate;
    private Timestamp lastModifiedDate;

}
