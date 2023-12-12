package com.quinbay.customergatewaymanagement.data;

import lombok.Data;

import java.util.List;

@Data
public class OrderVo {
    private String id;
    private Long customerId;
    private String shippingAddress;
    private List<ProductVo> productVoList;
}
