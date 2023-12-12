package com.quinbay.inventoryservicesmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quinbay.inventoryservicesmanagement.model.constant.FieldNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = FieldNames.PRODUCT_T)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = FieldNames.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = FieldNames.NAME, nullable = false)
    private String name;

    @Column(name = FieldNames.PRICE, nullable = false)
    private double price;

    @Column(name = FieldNames.QUANTITY, nullable = false)
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = FieldNames.CATEGORY_ID, nullable = false)
    @JsonIgnore
    private Category category;
}
