package com.nnthienphuc.intelligentbookstoreecommercewebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Publisher")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Publisher {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Publisher_ID")
    private Short publisherId;
    
    @Column(name = "Publisher_Name", nullable = false, length = 50)
    private String publisherName;
    
    public Publisher() {}
    public Short getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Short publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}