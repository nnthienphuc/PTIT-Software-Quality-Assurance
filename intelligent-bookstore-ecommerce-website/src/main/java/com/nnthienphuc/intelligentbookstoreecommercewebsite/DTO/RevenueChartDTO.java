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
public class RevenueChartDTO {
    private LocalDate orderDate;
    private BigDecimal dailyRevenue;
}
