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
        if (id == 0L) {
            member.orders.add(this)
            delivery.order = this
            orderItems.forEach { it.order = this }
        }
    }

    val orderDate: LocalDateTime = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    var status: OrderState = OrderState.ORDER

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    fun cancel() {
        if (delivery.status == DeliveryStatus.COMP) {
            throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")
        }

        status = OrderState.CANCEL
        for (orderItem in orderItems) {
            orderItem.cancel()
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    fun getTotalPrice(): Int {
        return orderItems.sumOf { it.getTotalPrice() }
    }
}
