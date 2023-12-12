package com.quinbay.customergatewaymanagement.service;

import com.quinbay.customergatewaymanagement.data.CategoryVo;
import com.quinbay.customergatewaymanagement.data.ProductVo;
import com.quinbay.customergatewaymanagement.data.RecommendationVo;

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

    List<RecommendationVo> getRecommendation(int id);
    String addRecommendation(RecommendationVo recommendationVo);
}
