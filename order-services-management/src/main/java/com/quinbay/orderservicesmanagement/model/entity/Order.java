package com.quinbay.orderservicesmanagement.model.entity;

import com.quinbay.orderservicesmanagement.model.vo.ProductVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "productpurchase")
public class Order {
    private String id;
    private Long customerId;
    private String shippingAddress;
    private List<ProductVo> productVoList;
}
