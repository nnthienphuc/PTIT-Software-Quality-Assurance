package com.nnthienphuc.intelligentbookstoreecommercewebsite.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Promotion")
@Data
public class Promotion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Promotion_ID")
    private Short promotionId;
    
    @Column(name = "Promotion_Name", nullable = false, length = 100)
    private String promotionName;
    
    @Column(name = "Condition", nullable = false)
    private BigDecimal condition;
    
    @Column(name = "Discount_Percent", nullable = false)
    private Double discountPercent;

    @OneToMany(mappedBy = "promotion")
    private Set<Order> orders = new LinkedHashSet<>();

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
}