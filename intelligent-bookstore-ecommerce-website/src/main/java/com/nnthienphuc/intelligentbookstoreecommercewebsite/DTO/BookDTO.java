package com.nnthienphuc.intelligentbookstoreecommercewebsite.DTO;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Author;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Cart;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Category;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.OrderDetail;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Publisher;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
	
    private String isbn;

    private String title;

    private Long categoryId;

    private Short authorId;

    private Short year_of_publication;

    private Short publisherId;
   
    private Integer quantity;

    private Boolean cover = false;

    private BigDecimal price;

    private Double discount_percent;

    private Boolean is_discount = false;

    private String description;
 
    private String url1;

    private String url2;
    
    private String url3;

    private String url4;

    private String url5;

    private Boolean is_discontinued = false;

    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    private Set<Cart> carts = new LinkedHashSet<>();
}
