package com.quinbay.orderservicesmanagement.controller;

import com.quinbay.orderservicesmanagement.model.vo.OrderVo;
import com.quinbay.orderservicesmanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/httpmethod")
public class HttpMethodController {
    @Autowired
    OrderService orderBean;

    @GetMapping("/order/history/{customerId}")
    public List<OrderVo> orderHistory(@PathVariable Long customerId)
    {
        return orderBean.customerOrderHistory(customerId);
    }
    @PostMapping("/customer/purchase")
    public OrderVo customerProductPurchase(@RequestBody OrderVo orderVo)
    {
        return orderBean.customerPurchase(orderVo);
    }
}
