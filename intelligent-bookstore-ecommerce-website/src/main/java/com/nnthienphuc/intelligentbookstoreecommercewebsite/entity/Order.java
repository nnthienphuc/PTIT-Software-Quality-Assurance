package com.nnthienphuc.intelligentbookstoreecommercewebsite.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    @ColumnDefault("getdate()")
    @Column(name = "order_date", nullable = false)
    private Instant orderDate;

    @Nationalized
    @Column(name = "receiver", nullable = false, length = 50)
    private String receiver;

    @Nationalized
    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Nationalized
    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Nationalized
    @Column(name = "order_status", nullable = false, length = 50)
    private String orderStatus;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();
}
