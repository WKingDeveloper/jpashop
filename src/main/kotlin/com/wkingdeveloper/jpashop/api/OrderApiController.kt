package com.wkingdeveloper.jpashop.api

import com.wkingdeveloper.jpashop.domain.Order
import com.wkingdeveloper.jpashop.domain.OrderItem
import com.wkingdeveloper.jpashop.repository.OrderRepository
import com.wkingdeveloper.jpashop.repository.OrderSearch
import com.wkingdeveloper.jpashop.repository.order.query.OrderItemQueryDto
import com.wkingdeveloper.jpashop.repository.order.query.OrderQueryDto
import com.wkingdeveloper.jpashop.repository.order.query.OrderQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiController(
    private val orderRepository: OrderRepository,
    private val orderQueryRepository: OrderQueryRepository
) {

    @GetMapping("/api/v1/orders")
    fun ordersV1(): List<Order> {
        val all = orderRepository.findAllByString(OrderSearch())
        all.forEach {
            it.member.name
            it.delivery.status
            val orderItems = it.orderItems
            orderItems.forEach { item -> item.item.name }
        }
        return all
    }

    @GetMapping("/api/v2/orders")
    fun ordersV2(): List<OrderDto> {
        val orders = orderRepository.findAllByString(OrderSearch())
        return orders.map { OrderDto(it) }
    }

    @GetMapping("/api/v3/orders")
    fun ordersV3(): List<OrderDto> {
        val orders = orderRepository.findAllWithItem()
        return orders.map { OrderDto(it) }
    }

    @GetMapping("/api/v3.1/orders")
    fun ordersV3_page(
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "100") limit: Int
    ): List<OrderDto> {
        val orders = orderRepository.findAllWithMemberDelivery(offset, limit)
        return orders.map { OrderDto(it) }
    }

    @GetMapping("/api/v4/orders")
    fun ordersV4(): List<OrderQueryDto> {
        return orderQueryRepository.findOrderQueryDtos()
    }

    @GetMapping("/api/v5/orders")
    fun ordersV5(): List<OrderQueryDto> {
        return orderQueryRepository.findAllByDto_optimization()
    }

    @GetMapping("/api/v6/orders")
    fun ordersV6(): List<OrderQueryDto> {
        val flats = orderQueryRepository.findAllByDto_flat()
        return flats.groupBy {
            OrderQueryDto(it.orderId, it.name, it.orderDate, it.orderStatus, it.address)
        }.map { (orderQueryDto, items) ->
            OrderQueryDto(
                orderQueryDto.orderId,
                orderQueryDto.name,
                orderQueryDto.orderDate,
                orderQueryDto.orderStatus,
                orderQueryDto.address,
            ).apply {
                orderItems = items.map {
                    OrderItemQueryDto(it.orderId, it.itemName, it.itemPrice, it.count)
                }
            }
        }
    }

    class OrderDto(
        order: Order
    ) {
        val orderId = order.id
        val name = order.member.name
        val orderDate = order.orderDate
        val orderStatus = order.status
        val address = order.delivery.address
        val orderItems = order.orderItems.map { OrderItemDto(it) }
    }

    class OrderItemDto(
        orderItem: OrderItem
    ) {
        val itemName = orderItem.item.name
        val orderPrice = orderItem.orderPrice
        val count = orderItem.count
    }
}
