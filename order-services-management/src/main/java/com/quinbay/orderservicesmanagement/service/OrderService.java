package com.quinbay.orderservicesmanagement.service;

import com.quinbay.orderservicesmanagement.model.vo.OrderVo;

import java.util.List;

public interface OrderService {
    OrderVo customerPurchase(OrderVo orderVo);
    List<OrderVo> customerOrderHistory(Long customerId);
}
