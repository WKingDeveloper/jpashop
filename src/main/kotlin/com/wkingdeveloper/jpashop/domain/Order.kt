package com.wkingdeveloper.jpashop.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Id @GeneratedValue
    @Column(name = "order_id")
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: List<OrderItem>,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    val delivery: Delivery,

    ) {

    init {
        member.orders.add(this)
        delivery.order = this
    }

    val orderDate: LocalDateTime = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    var status: OrderState = OrderState.ORDER
}
