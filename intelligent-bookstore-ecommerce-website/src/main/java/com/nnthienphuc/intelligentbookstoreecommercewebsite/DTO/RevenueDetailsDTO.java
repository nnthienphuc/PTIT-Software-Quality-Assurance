package com.nnthienphuc.intelligentbookstoreecommercewebsite.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueDetailsDTO {
    private LocalDate orderDate;
    private long orderCount;
    private BigDecimal totalRevenue;
    private BigDecimal growthRate;

}
