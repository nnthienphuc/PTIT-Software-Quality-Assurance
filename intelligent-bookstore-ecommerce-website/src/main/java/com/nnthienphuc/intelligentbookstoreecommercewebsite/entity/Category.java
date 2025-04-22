package com.nnthienphuc.intelligentbookstoreecommercewebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Category")
public class Category {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "Category_Id", nullable = false)
    private Long categoryId;

    @Column(name = "Category_Name", nullable = false)
    private String categoryName;

    
}