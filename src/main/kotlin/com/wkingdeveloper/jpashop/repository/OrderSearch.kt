package com.wkingdeveloper.jpashop.repository

import com.wkingdeveloper.jpashop.domain.OrderState

data class OrderSearch(
    val memberName: String? = null,
    val orderStatus: OrderState? = null
) {
}
