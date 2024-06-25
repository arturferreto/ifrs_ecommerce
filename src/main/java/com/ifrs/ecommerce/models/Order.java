package com.ifrs.ecommerce.models;

import com.ifrs.ecommerce.enums.FulfillmentStatusEnum;
import com.ifrs.ecommerce.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", nullable = true)
    private Discount discount;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PaymentStatusEnum paymentStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private FulfillmentStatusEnum fulfillmentStatus;

    @Column(nullable = false)
    private Double totalAmount;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}