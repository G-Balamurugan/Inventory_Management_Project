package com.quinbay.personaliseservicesmanagement.service;

import com.quinbay.personaliseservicesmanagement.model.vo.RecommendationVo;

import java.util.List;

public interface RecommendationService {
    List<RecommendationVo> getRecommendation(int id);
    String addRecommendation(RecommendationVo recommendationVo);
}
