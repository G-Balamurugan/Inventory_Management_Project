package com.quinbay.customergatewaymanagement.serviceImplementation;

import com.quinbay.customergatewaymanagement.data.CategoryVo;
import com.quinbay.customergatewaymanagement.data.ProductVo;
import com.quinbay.customergatewaymanagement.data.RecommendationVo;
import com.quinbay.customergatewaymanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service("productBean")
public class ProductServiceImplementation implements ProductService {
    @Autowired
    private RestTemplate restTemplate;
    private String baseUrl = "http://localhost:8085/httpmethod";
    private String baseUrlRecommendation = "http://localhost:8086/httpmethod";

    public List<String> productTableInsert(List<ProductVo> productVoList)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<ProductVo>> entity = new HttpEntity<>(productVoList , headers);
        return restTemplate.exchange(baseUrl+"/product/add",HttpMethod.POST,entity,List.class).getBody();
    }
    public List<String> categoryTableInsert(List<CategoryVo> categoryVoList)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<CategoryVo>> entity = new HttpEntity<>(categoryVoList , headers);
        return restTemplate.exchange(baseUrl+"/category/add" , HttpMethod.POST , entity , List.class).getBody();
    }
    public String deleteProductEntry(Long id)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Long> entity = new HttpEntity<>(id , headers);
        return restTemplate.exchange(baseUrl+"/product/delete/"+id,HttpMethod.DELETE,entity,String.class).getBody();
    }
    public String deleteCategoryEntry(Long id)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Long> entity = new HttpEntity<>(id , headers);
        return restTemplate.exchange(baseUrl+"/category/delete/"+id,HttpMethod.DELETE,entity,String.class).getBody();
    }
    public String updateProduct(ProductVo productVo , long productId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductVo> entity = new HttpEntity<>(productVo , headers);
        return restTemplate.exchange(baseUrl+"product/update/"+productId,HttpMethod.PUT,entity,String.class).getBody();
    }
    public String updateCategory(CategoryVo categoryVo , long categoryId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CategoryVo> entity = new HttpEntity<>(categoryVo , headers);
        return restTemplate.exchange(baseUrl+"category/update/"+categoryId,HttpMethod.PUT,entity,String.class).getBody();
    }
    public List<ProductVo> getProductList()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(baseUrl+"/product/list").build();
        return restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity,List.class).getBody();
    }
    public List<CategoryVo> getCategoryList()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(baseUrl+"/category/list").build();
        return restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity,List.class).getBody();
    }
    public List<ProductVo> productByFilter(Long productId , String category , String productName)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/product/filter");
        if (productId != -1)
            builder.queryParam("id", productId);
        if (!category.equals(""))
            builder.queryParam("category", category);
        if (!productName.equals(""))
            builder.queryParam("productName", productName);
        ResponseEntity<List<ProductVo>> responseEntity = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProductVo>>() {}
        );
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            return Collections.emptyList();
        }
    }
    public List<RecommendationVo> getRecommendation(int customerId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity(headers);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(baseUrlRecommendation+"/recommendation/"+customerId).build();
        return restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity,List.class).getBody();
    }
    public String addRecommendation(RecommendationVo recommendationVo)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RecommendationVo> entity = new HttpEntity<>(recommendationVo,headers);
        return restTemplate.exchange(baseUrlRecommendation+"/recommendation/add",HttpMethod.POST,entity,String.class).getBody();
    }
}

