package com.quinbay.personaliseservicesmanagement.dao.api;

import com.quinbay.personaliseservicesmanagement.model.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    @Override
    Recommendation save(Recommendation recommendation);

    @Query("SELECT recommendation_details FROM Recommendation recommendation_details " +
            "WHERE recommendation_details.customerId = :customerId")
    List<Recommendation> findRecommendationByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT recommendation_details FROM Recommendation recommendation_details " +
            "WHERE recommendation_details.customerId = :customerId " +
            "AND recommendation_details.categoryId = :categoryId")
    List<Recommendation> findRecommendationByCustomerCategoryId(
            @Param("customerId") int customerId,
            @Param("categoryId") int categoryId);
}
