package com.wkingdeveloper.jpashop.domain

import com.wkingdeveloper.jpashop.domain.item.Item
import jakarta.persistence.*

@Entity
class OrderItem(
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    val id: Long = 0,
    val orderPrice: Int,
    val count: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    val order: Order,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    val item: Item
) {


}