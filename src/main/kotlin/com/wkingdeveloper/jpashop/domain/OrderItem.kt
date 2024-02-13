package com.wkingdeveloper.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.wkingdeveloper.jpashop.domain.item.Item
import javax.persistence.*

@Entity
class OrderItem(
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    val id: Long = 0,
    val orderPrice: Int,
    val count: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    val item: Item
) {
    init {
        if (id == 0L) {
            item.removeStock(count)
        }
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    lateinit var order: Order

    //==비즈니스 로직==//
    fun cancel() = item.addStock(count)

    //==조회 로직==//
    fun getTotalPrice(): Int = orderPrice * count


}
