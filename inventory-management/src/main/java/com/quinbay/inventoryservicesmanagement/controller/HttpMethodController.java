package com.quinbay.inventoryservicesmanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.quinbay.inventoryservicesmanagement.model.vo.CategoryVo;
import com.quinbay.inventoryservicesmanagement.model.vo.ProductVo;
import com.quinbay.inventoryservicesmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/httpmethod")
public class HttpMethodController {
    @Autowired
    ProductService productBean;

    @GetMapping("/product/list")
    @Cacheable(value="productList")
    public List<ProductVo> getProductFindAll()
    {
        return productBean.getProductList();
    }
    @GetMapping("/category/list")
    @Cacheable(value="categoryList")
    public List<CategoryVo> getCategoryFindAll()
    {
        return productBean.getCategoryList();
    }
    @GetMapping("/product/filter")
    public List<ProductVo> getProductByFilter(@RequestParam (required = false) Long id , @RequestParam (required = false) String category , @RequestParam (required = false) String productName)
    {
        return productBean.productByFilter(id , category , productName);
    }
    @GetMapping("/product/quantity/{productId}")
    public int getProductQuantity(@PathVariable Long productId)
    {
        return productBean.productQuantity(productId);
    }
    @PostMapping("/product/add")
    public List<String> productAddDetails(@RequestBody List<ProductVo> productVoList)
    {
        return productBean.productTableInsert(productVoList);
    }
    @PostMapping("/category/add")
    public List<String> categoryTableEntry(@RequestBody List<CategoryVo> categoryVoList)
    {
        return productBean.categoryTableInsert(categoryVoList);
    }
    @PostMapping("/publish")
    public void publishKafka(@RequestBody ProductVo productVo)throws JsonProcessingException
    {
        productBean.sendMessage(productVo);
    }
    @PutMapping("/product/update/{productId}")
    public String productUpdateDetails(@RequestBody ProductVo productVo ,@PathVariable long productId)
    {
        return productBean.updateProduct(productVo,productId);
    }
    @PutMapping("category/update/{categoryId}")
    public String categoryUpdateDetails(@RequestBody CategoryVo categoryVo , @PathVariable long categoryId)
    {
        return productBean.updateCategory(categoryVo,categoryId);
    }
    @PutMapping("/product/update/quantity/{productId}/{purchaseQuantity}")
    public ProductVo updateProductQuantity(@PathVariable Long productId , @PathVariable int purchaseQuantity)
    {
        return productBean.updateQuantity(productId , purchaseQuantity);
    }
    @DeleteMapping("/product/delete/{productId}")
    public String productDeleteDetails(@PathVariable int productId)
    {
        return productBean.deleteProductEntry(Long.valueOf(productId));
    }
    @DeleteMapping("/category/delete/{categoryId}")
    public String categoryDeleteDetails(@PathVariable Long categoryId)
    {
        return productBean.deleteCategoryEntry(categoryId);
    }
}
