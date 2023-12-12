package com.quinbay.inventoryservicesmanagement.model.vo;

import com.quinbay.inventoryservicesmanagement.model.entity.Category;
import lombok.Data;

@Data
public class ProductVo {
    private long id;
    private String name;
    private double price;
    private int quantity;
    private Category category;
}
