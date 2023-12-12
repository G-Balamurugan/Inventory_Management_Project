package com.quinbay.personaliseservicesmanagement.model.entity;

import com.quinbay.personaliseservicesmanagement.model.constant.FieldNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = FieldNames.RECOMMENDATION_T)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = FieldNames.CUSTOMER_ID, nullable = false)
    private int customerId;

    @Column(name = FieldNames.CATEGORY_ID, nullable = false)
    private int categoryId;
}
