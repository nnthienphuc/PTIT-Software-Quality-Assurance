package com.nnthienphuc.intelligentbookstoreecommercewebsite.service;

import java.util.List;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Author;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Category;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Promotion;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.repository.PromotionRepository;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository PromotionRepository;


    public Page<Promotion> getAllPromotions(Pageable pageable) {
        return PromotionRepository.findAll(pageable);
    }
    
    public Page<Promotion> searchPromotions(String keyword, Pageable pageable) {
        return PromotionRepository.findByPromotionNameContainingIgnoreCase(keyword, pageable);
    }


    public Promotion getPromotionById(Long id) {
        return PromotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với id: " + id));
    }


    public Promotion savePromotion(Promotion Promotion) {
    	if(Promotion.getDiscountPercent() != null && Promotion.getDiscountPercent() >= 1)
    		Promotion.setDiscountPercent(Promotion.getDiscountPercent()/100); 
        return PromotionRepository.save(Promotion);
    }


    public void deletePromotion(Long id) {
        PromotionRepository.deleteById(id);
    }
    public List<Promotion> getAllCategoriesNoPaging() {
        return PromotionRepository.findAll();
    }
    
}