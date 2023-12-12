package com.quinbay.personaliseservicesmanagement.ServiceImplementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinbay.personaliseservicesmanagement.dao.api.RecommendationRepository;
import com.quinbay.personaliseservicesmanagement.model.entity.Recommendation;
import com.quinbay.personaliseservicesmanagement.model.vo.ProductVo;
import com.quinbay.personaliseservicesmanagement.model.vo.RecommendationVo;
import com.quinbay.personaliseservicesmanagement.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("recommendationBean")
public class RecommendationServiceImplementation implements RecommendationService {
    @Autowired
    RecommendationRepository recommendationRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics="com.quinbay.product.create",groupId = "group-id")
    public void listen(String message) throws JsonProcessingException
    {
        ObjectMapper objectMapper=new ObjectMapper();
        ProductVo resProduct=objectMapper.readValue(message,ProductVo.class);
        System.out.println("Received message in group - group-id "+resProduct);
    }
    public List<RecommendationVo> getRecommendation(int customerId)
    {
        List<Recommendation> recommendationList = recommendationRepository.findRecommendationByCustomerId(customerId);
        return objectMapper.convertValue(recommendationList , List.class);
    }
    public String addRecommendation(RecommendationVo recommendationVo)
    {
        List<Recommendation> recommendationList = recommendationRepository.findRecommendationByCustomerCategoryId(recommendationVo.getCustomerId(),recommendationVo.getCategoryId());
        if(recommendationList.isEmpty())
        {
            Recommendation recommendation = objectMapper.convertValue(recommendationVo,Recommendation.class);
            try {
                recommendationRepository.save(recommendation);
            }catch (Exception exceptionArised)
            {
                System.out.println("Exception Catched => " + exceptionArised);
            }
            return "Added Successfully";
        }
        return "Already Exists";
    }
}
