package com.wkingdeveloper.jpashop.repository.order

import com.wkingdeveloper.jpashop.domain.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderDataJpaRepository :JpaRepository<Order,Long> {
}