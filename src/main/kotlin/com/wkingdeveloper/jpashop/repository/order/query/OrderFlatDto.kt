package com.wkingdeveloper.jpashop.repository.order.query

import com.fasterxml.jackson.annotation.JsonIgnore
import com.wkingdeveloper.jpashop.domain.Address
import com.wkingdeveloper.jpashop.domain.OrderState
import java.time.LocalDateTime

data class OrderFlatDto(
    @JsonIgnore
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderState,
    val address: Address,
    val itemName: String,
    val itemPrice: Int,
    val count: Int
)
