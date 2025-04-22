package com.nnthienphuc.intelligentbookstoreecommercewebsite.repository;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT b FROM Cart b WHERE b.user.userId = :customerId")
    List <Cart> findByCustomerId(String customerId);

    @Query("SELECT b FROM Cart b WHERE b.user.userId = :customerId AND b.isbn.isbn = :isbn")
    Cart findCartByUserIdAndIsbn(String customerId, String isbn);

    @Transactional
    @Modifying
    @Query("UPDATE Cart c SET c.quantity = c.quantity + :quantity WHERE c.user.userId = :userId AND c.isbn.isbn = :isbn")
    void updateCartQuantity(@Param("quantity") Short quantity,
                            @Param("userId") String userId,
                            @Param("isbn") String isbn);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user.userId = :userId AND c.isbn.isbn = :isbn")
    void deleteCartItem(@Param("userId") String userId,
                        @Param("isbn") String isbn);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);



}
