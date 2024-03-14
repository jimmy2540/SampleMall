package com.chun.samplemall.controller;

import com.chun.samplemall.constant.ProductCategory;
import com.chun.samplemall.dto.ProductQueryParams;
import com.chun.samplemall.dto.ProductRequest;
import com.chun.samplemall.model.Product;
import com.chun.samplemall.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.chun.samplemall.util.Page;
import java.util.List;

@Validated
@RestController
public class ProductController {
    @Autowired
    private ProductService  productService;
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
       Product product = productService.getProducctById(productId);

        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
@GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            //條件
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            //排序
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            //分頁
            @RequestParam(defaultValue = "5") @Max(100) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
        ){
    ProductQueryParams productQueryParams = new ProductQueryParams();
    productQueryParams.setSearch(search);
    productQueryParams.setCategory(category);
    productQueryParams.setOderBy(orderBy);
    productQueryParams.setSort(sort);
    productQueryParams.setLimit(limit);
    productQueryParams.setOffset(offset);
    List<Product> productList = productService.getProducts(productQueryParams);
    Integer total = productService.countProducts(productQueryParams);
    Page<Product> page = new Page<>();
    page.setLimit(limit);
    page.setOffset(offset);
    page.setTotal(total);
    page.setResults(productList);
        return ResponseEntity.status(HttpStatus.OK).body(page);
}

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
       Integer productId = productService.createProduct(productRequest);

       Product product = productService.getProducctById(productId);

       return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
        @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable @Valid Integer productId,@RequestBody @Valid ProductRequest productRequest){

        //檢查產品是否存在
        Product product = productService.getProducctById(productId);
        if (product == null){return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
        //修改產品
        productService.updateProductById(productId,productRequest);
        //回傳修改後的結果
        Product updateproduct = productService.getProducctById(productId);
        return  ResponseEntity.status(HttpStatus.OK).body(updateproduct);
    }
    @DeleteMapping("products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
