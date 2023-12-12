package com.quinbay.inventoryservicesmanagement.doa.api;

import com.quinbay.inventoryservicesmanagement.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Long>{
    @Override
    List<Category> findAll();
    @Override
    Category save(Category category);
}
