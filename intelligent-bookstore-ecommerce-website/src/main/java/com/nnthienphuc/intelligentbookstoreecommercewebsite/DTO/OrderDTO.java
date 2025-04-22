package com.nnthienphuc.intelligentbookstoreecommercewebsite.DTO;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.format.annotation.NumberFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	
	private Long orderId;

    private String userName;

    private String promotionName;
 
    private Instant orderDate;
 
    private String receiver;
 
    private String address;
 
    private String paymentMethod;
 
    private String orderStatus;
    @NumberFormat(pattern = "#,###")
    private BigDecimal totalPrice;

}
