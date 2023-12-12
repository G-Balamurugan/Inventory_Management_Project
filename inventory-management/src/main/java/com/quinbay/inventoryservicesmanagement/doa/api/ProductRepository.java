package com.quinbay.inventoryservicesmanagement.doa.api;

import com.quinbay.inventoryservicesmanagement.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    List<Product> findAll();

    @Override
    Product save(Product product);

    @Query("SELECT product_details FROM Product product_details " +
            "WHERE " +
            "   (product_details.name = :name AND product_details.category.name = :category AND product_details.id = :productId )" +
            "OR (:name = null AND product_details.category.name = :category AND product_details.id = :productId )" +
            "OR (product_details.name = :name AND product_details.category.name = :category AND :productId = null )"+
            "OR (product_details.name = :name AND :category = null AND product_details.id = :productId )"+
            "OR (:name is null AND :category is null AND :productId is null)" +
            "OR (:name = null AND :category = null AND product_details.id = :productId )"+
            "OR (:name = null AND product_details.category.name = :category AND :productId = null )"+
            "OR (product_details.name = :name AND :category = null AND :productId = null )")
    List<Product> findProductIdCategoryName(@Param("productId") Long productId, @Param("category") String category, @Param("name") String name);

    @Query("SELECT quantity FROM Product product_details " +
            "WHERE product_details.id = :productId")
    int findProductQuantity(@Param("productId") Long productId);
}
