package com.quinbay.customergatewaymanagement.service;

import com.quinbay.customergatewaymanagement.data.OrderVo;

import java.util.List;

public interface OrderService {
    OrderVo customerPurchase(OrderVo orderVo);
    List<OrderVo> customerOrderHistory(Long customerId);
}
