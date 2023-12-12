package com.quinbay.personaliseservicesmanagement.controller;


import com.quinbay.personaliseservicesmanagement.model.vo.RecommendationVo;
import com.quinbay.personaliseservicesmanagement.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/httpmethod")
public class HttpMethodController {
    @Autowired
    RecommendationService recommendationBean;
    @GetMapping("/recommendation/{customerId}")
    public List<RecommendationVo> getRecommendationByProduct(@PathVariable Long customerId)
    {
        return recommendationBean.getRecommendation(customerId.intValue());
    }
    @PostMapping("/recommendation/add")
    public String addRecommendationDetails(@RequestBody RecommendationVo recommendationVo){
        return recommendationBean.addRecommendation(recommendationVo);
    }
}
