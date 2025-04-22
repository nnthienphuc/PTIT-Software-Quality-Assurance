package com.nnthienphuc.intelligentbookstoreecommercewebsite.DTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.NumberFormat;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.OrderDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
	private Long orderId;

    private String userName;

    private String promotionName;
 
    private Instant orderDate;
 
    private String receiver;
 
    private String address;
 
    private String paymentMethod;
 
    private String orderStatus;
  
    private BigDecimal totalPrice;
    
    private List<String> bookName;
    private List<String> bookImg;
    private List<Short> quantity;
    private List<BigDecimal> price;
}
