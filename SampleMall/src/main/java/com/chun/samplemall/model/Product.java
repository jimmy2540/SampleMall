package com.chun.samplemall.model;

import com.chun.samplemall.constant.ProductCategory;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
@Data
public class Product {
    private Integer productId;
    private String productName;
    private ProductCategory category;
    private  String imageUrl;
    private  Integer price;
    private  Integer stock;
    private  String description;
    private  Timestamp createDate;
    private Timestamp lastModifiedDate;

}
