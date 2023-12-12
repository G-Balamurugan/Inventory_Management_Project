package com.quinbay.customergatewaymanagement.controller;

import com.quinbay.customergatewaymanagement.data.CategoryVo;
import com.quinbay.customergatewaymanagement.data.OrderVo;
import com.quinbay.customergatewaymanagement.data.ProductVo;
import com.quinbay.customergatewaymanagement.data.RecommendationVo;
import com.quinbay.customergatewaymanagement.service.OrderService;
import com.quinbay.customergatewaymanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/httpmethod")
public class HttpMethodController {
    @Autowired
    ProductService productBean;
    @Autowired
    OrderService orderBean;

    @GetMapping("/order/history/{customerId}")
    public List<OrderVo> orderHistory(@PathVariable Long customerId)
    {
        return orderBean.customerOrderHistory(customerId);
    }
    @GetMapping("/product/list")
    public List<ProductVo> getProductFindAll()
    {
        return productBean.getProductList();
    }
    @GetMapping("/category/list")
    public List<CategoryVo> getCategoryFindAll()
    {
        return productBean.getCategoryList();
    }
    @GetMapping("/product/filter")
    public List<ProductVo> getProductByFilter(@RequestParam (required = false , defaultValue = "-1") Long id , @RequestParam (required = false , defaultValue = "") String category , @RequestParam (required = false , defaultValue = "") String productName)
    {
        return productBean.productByFilter(id , category , productName);
    }
    @GetMapping("/recommendation/{customerId}")
    public List<RecommendationVo> getRecommendationByProduct(@PathVariable Long customerId)
    {
        return productBean.getRecommendation(customerId.intValue());
    }
    @PostMapping("/customer/purchase")
    public OrderVo customerProductPurchase(@RequestBody OrderVo orderVo)
    {
        return orderBean.customerPurchase(orderVo);
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
    @PostMapping("/recommendation/add")
    public String addRecommendationDetails(@RequestBody RecommendationVo recommendationVo)
    {
        return productBean.addRecommendation(recommendationVo);
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
