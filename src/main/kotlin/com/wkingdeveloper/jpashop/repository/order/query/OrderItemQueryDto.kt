package com.wkingdeveloper.jpashop.repository.order.query

import com.fasterxml.jackson.annotation.JsonIgnore

data class OrderItemQueryDto(
    @JsonIgnore
    val orderId: Long,
    val itemName: String,
    val itemPrice: Int,
    val count: Int
)
