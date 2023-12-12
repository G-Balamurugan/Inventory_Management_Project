package com.quinbay.orderservicesmanagement.serviceImplementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinbay.orderservicesmanagement.dao.api.OrderRepository;
import com.quinbay.orderservicesmanagement.model.entity.Order;
import com.quinbay.orderservicesmanagement.model.vo.OrderVo;
import com.quinbay.orderservicesmanagement.model.vo.ProductVo;
import com.quinbay.orderservicesmanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("orderBean")
public class OrderServiceImplementation implements OrderService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    OrderRepository orderRepository;
    private String baseUrl = "http://localhost:8085/httpmethod";
    private ObjectMapper objectMapper = new ObjectMapper();

    private String generateUniqueOrderId() {
            return UUID.randomUUID().toString();
    }
    public OrderVo customerPurchase(OrderVo orderVo){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        for(ProductVo productVo : orderVo.getProductVoList())
        {
            UriComponents builder = UriComponentsBuilder.fromHttpUrl(baseUrl+"/product/quantity/"+productVo.getId()).build();
            int availabeQuantity = restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity,Integer.class).getBody();

            if(availabeQuantity == -1)
            {
                productVo.setStatus("Product Not Found");
                continue;
            }
            else if(availabeQuantity < productVo.getQuantity())
            {
                productVo.setStatus("Product Out of Stock");
                continue;
            }
            ProductVo updatedProductVo = restTemplate.exchange(baseUrl+"/product/update/quantity/"+productVo.getId()+"/"+productVo.getQuantity() , HttpMethod.PUT,entity,ProductVo.class).getBody();
            productVo.setStatus("Purchased Product");
            productVo.setName(updatedProductVo.getName());
            productVo.setPrice(updatedProductVo.getPrice() * productVo.getQuantity());
        }
        orderVo.setId(generateUniqueOrderId());
        Order order = objectMapper.convertValue(orderVo , Order.class);
        try {
            orderRepository.save(order);
        }catch (Exception exceptionArised)
        {
            System.out.println("Exception Catched => " + exceptionArised);
        }
        return orderVo;
    }
    public List<OrderVo> customerOrderHistory(Long customerId){
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        return orders.stream()
                .map(order -> objectMapper.convertValue(order, OrderVo.class))
                .collect(Collectors.toList());
    }
}
