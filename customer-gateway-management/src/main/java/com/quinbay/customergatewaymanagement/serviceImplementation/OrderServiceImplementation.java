package com.quinbay.customergatewaymanagement.serviceImplementation;

import com.quinbay.customergatewaymanagement.data.OrderVo;
import com.quinbay.customergatewaymanagement.service.OrderService;
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

@Service("orderBean")
public class OrderServiceImplementation implements OrderService {
    @Autowired
    private RestTemplate restTemplate;
    private String baseUrl = "http://localhost:8087/httpmethod";
    public  OrderVo customerPurchase(OrderVo orderVo){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderVo> entity = new HttpEntity<>(orderVo,headers);
        return restTemplate.exchange(baseUrl+"/customer/purchase",HttpMethod.POST,entity,OrderVo.class).getBody();
    }
    public List<OrderVo> customerOrderHistory(Long customerId){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(baseUrl+"/order/history/"+customerId).build();
        return restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity,List.class).getBody();
    }
}
