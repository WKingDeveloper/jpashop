package com.wkingdeveloper.jpashop.repository.order.simplequery

import com.wkingdeveloper.jpashop.domain.Address
import com.wkingdeveloper.jpashop.domain.OrderState
import java.time.LocalDateTime

data class OrderSimpleQueryDTO(
    val orderId: Long,
    val name: String,
    val orderData: LocalDateTime,
    val orderState: OrderState,
    val address: Address,
)
