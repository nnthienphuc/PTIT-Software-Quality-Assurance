package com.nnthienphuc.intelligentbookstoreecommercewebsite.repository;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Category;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :fullName, '%'))")
    List<User> findByFullNameContainingIgnoreCase(@Param("fullName") String fullName);

 // Tìm kiếm theo categoryName chứa keyword (không phân biệt hoa thường)
    Page<User> findByFullNameContainingIgnoreCase(String fullNameKeyword, Pageable pageable);
    Page<User> findByUserId(String fullNameKeyword, Pageable pageable);
     // Optional: Nên dùng Optional để xử lý null safety
     Optional<User> findByUserId(String userId);
}
