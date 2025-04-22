package com.nnthienphuc.intelligentbookstoreecommercewebsite.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Author;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Publisher;


@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
	 // Tìm kiếm theo categoryName chứa keyword (không phân biệt hoa thường)
	 Page<Publisher> findByPublisherNameContainingIgnoreCase(String keyword, Pageable pageable);

}
