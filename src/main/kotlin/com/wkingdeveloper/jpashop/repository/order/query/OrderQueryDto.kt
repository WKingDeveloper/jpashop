package com.wkingdeveloper.jpashop.repository.order.query

import com.fasterxml.jackson.annotation.JsonIgnore
import com.wkingdeveloper.jpashop.domain.Address
import com.wkingdeveloper.jpashop.domain.OrderState
import java.time.LocalDateTime

data class OrderQueryDto(
    @JsonIgnore
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderState,
    val address: Address,
) {
    var orderItems: List<OrderItemQueryDto> = emptyList()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderQueryDto

        if (orderId != other.orderId) return false

        return true
    }

    override fun hashCode(): Int {
        return orderId.hashCode()
    }


}
