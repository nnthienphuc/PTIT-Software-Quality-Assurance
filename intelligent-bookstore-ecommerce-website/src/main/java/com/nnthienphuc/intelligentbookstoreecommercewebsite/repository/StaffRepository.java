package com.nnthienphuc.intelligentbookstoreecommercewebsite.repository;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Category;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Staff;
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
public interface StaffRepository extends JpaRepository<Staff, String> {
    Staff findByEmail(String email);

    @Query("SELECT s FROM User s WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :fullName, '%'))")
    List<Staff> findByFullNameContainingIgnoreCase(@Param("fullName") String fullName);

 // Tìm kiếm theo categoryName chứa keyword (không phân biệt hoa thường)
 	 Page<Staff> findByFullNameContainingIgnoreCaseOrStaffIdContainingIgnoreCase(String keyword,String staffId, Pageable pageable);
     // Optional: Nên dùng Optional để xử lý null safety
     Optional<Staff> findByStaffId(String staffId);
     Page<Staff> findByFullNameContainingIgnoreCase(String fullNameKeyword, Pageable pageable);
     Page<Staff> findByStaffId(String fullNameKeyword, Pageable pageable);
}
