package com.nnthienphuc.intelligentbookstoreecommercewebsite.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.DTO.RevenueDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.DTO.OrderDTO;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Author;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Book;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	// Tìm theo tên người đặt
    @Query("SELECT o FROM Order o WHERE o.user.fullName LIKE %:buyerName%")
    Page<Order> findByBuyerName(@Param("buyerName") String buyerName, Pageable pageable);
      
    Page<Order> findByReceiverContainingIgnoreCaseAndOrderStatusContainingIgnoreCase(String receiver,String orderStatus, Pageable pageable);
    // Lọc theo trạng thái
    Page<Order> findByOrderStatus(String status, Pageable pageable);

    @Query("SELECT o.orderStatus, COUNT(o) " +
    	       "FROM Order o " +
    	       "GROUP BY o.orderStatus")
    	List<Object[]> countOrdersByStatus();
    // Đếm số lượng theo trạng thái
    Long countByOrderStatus(String status);


    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
    List<Order> findByUserId(String userId);
}