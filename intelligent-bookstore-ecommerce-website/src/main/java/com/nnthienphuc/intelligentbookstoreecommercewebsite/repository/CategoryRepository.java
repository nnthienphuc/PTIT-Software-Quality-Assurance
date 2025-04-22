package com.nnthienphuc.intelligentbookstoreecommercewebsite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Author;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	 // Tìm kiếm theo categoryName chứa keyword (không phân biệt hoa thường)
	 Page<Category> findByCategoryNameContainingIgnoreCase(String keyword, Pageable pageable);
    // Optional: Nên dùng Optional để xử lý null safety
    Optional<Category> findByCategoryId(Long categoryId);
}