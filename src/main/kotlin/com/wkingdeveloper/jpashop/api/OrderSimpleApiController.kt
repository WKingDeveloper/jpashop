package com.wkingdeveloper.jpashop.api

import com.wkingdeveloper.jpashop.domain.Address
import com.wkingdeveloper.jpashop.domain.Order
import com.wkingdeveloper.jpashop.domain.OrderState
import com.wkingdeveloper.jpashop.repository.OrderRepository
import com.wkingdeveloper.jpashop.repository.OrderSearch
import com.wkingdeveloper.jpashop.repository.order.simplequery.OrderSimpleQueryDTO
import com.wkingdeveloper.jpashop.repository.order.simplequery.OrderSimpleQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

/**
 * X to One
 * Order
 * Order -> Member
 * Order -> Delivery
 */

@RestController
class OrderSimpleApiController(
    private val orderRepository: OrderRepository,
    private val orderSimpleQueryRepository: OrderSimpleQueryRepository
) {
    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): List<Order> {
        val all = orderRepository.findAllByString(OrderSearch())
        all.forEach {
            it.member.name // Lazy 강제 초기화
            it.delivery.address // Lazy 강제 초기화
        }
        return all
    }

    @GetMapping("/api/v2/simple-orders")
    fun ordersV2(): List<SimpleOrderDTO> {
        val all = orderRepository.findAllByString(OrderSearch())
        return all.map { SimpleOrderDTO(it) }
    }

    @GetMapping("/api/v3/simple-orders")
    fun ordersV3(): List<SimpleOrderDTO> {
        val all = orderRepository.findAllWithMemberDelivery()
        return all.map { SimpleOrderDTO(it) }
    }

    @GetMapping("/api/v4/simple-orders")
    fun ordersV4(): List<OrderSimpleQueryDTO> {
        return orderSimpleQueryRepository.findOrderDtos()
    }


    class SimpleOrderDTO(
        order: Order
    ) {
        val orderId: Long = order.id
        val name: String = order.member.name // Lazy 초기화
        val orderData: LocalDateTime = order.orderDate
        val orderState: OrderState = order.status
        val address: Address = order.delivery.address // Lazy 초기화
    }
}
