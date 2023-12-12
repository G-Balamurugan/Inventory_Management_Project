package com.quinbay.inventoryservicesmanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.quinbay.inventoryservicesmanagement.model.vo.CategoryVo;
import com.quinbay.inventoryservicesmanagement.model.vo.ProductVo;

import java.util.List;

public interface ProductService {
    List<String> productTableInsert(List<ProductVo> productVoList);
    List<String> categoryTableInsert(List<CategoryVo> categoryVoList);

    String deleteProductEntry(Long id);
    String deleteCategoryEntry(Long id);

    String updateProduct(ProductVo productVo , long productId);
    String updateCategory(CategoryVo categoryVo , long categoryId);

    List<ProductVo> getProductList();
    List<CategoryVo> getCategoryList();
    List<ProductVo> productByFilter(Long productId , String category , String productName);

    int productQuantity(Long productId);
    ProductVo updateQuantity(Long ProductId , int purchaseQuantity);
    void sendMessage(ProductVo productVo) throws JsonProcessingException;
}
