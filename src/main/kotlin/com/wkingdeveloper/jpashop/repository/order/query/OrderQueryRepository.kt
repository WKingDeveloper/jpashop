package com.wkingdeveloper.jpashop.repository.order.query

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class OrderQueryRepository(
    private val em: EntityManager
) {

    fun findOrderQueryDtos(): List<OrderQueryDto> {
        val result = findOrders()
        result.forEach {
            val orderItems = findOrderItems(it.orderId)
            it.orderItems = orderItems
        }
        return result
    }

    private fun findOrders(): List<OrderQueryDto> {
        return em.createQuery(
            "select new com.wkingdeveloper.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate,o.status,d.address)" +
                    " from Order o" +
                    " join o.member m" +
                    " join o.delivery d", OrderQueryDto::class.java
        ).resultList
    }

    private fun findOrderItems(orderId: Long): List<OrderItemQueryDto> {
        return em.createQuery(
            "select new com.wkingdeveloper.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id,i.name,oi.orderPrice,oi.count)" +
                    " from OrderItem oi" +
                    " join oi.item i" +
                    " where oi.order.id = :orderId", OrderItemQueryDto::class.java
        )
            .setParameter("orderId", orderId)
            .resultList
    }

    fun findAllByDto_optimization(): List<OrderQueryDto> {
        val result = findOrders()
        val orderIds = result.map { it.orderId }
        val orderItemMap = findOrderItemMap(orderIds)

        result.forEach { it.orderItems = orderItemMap[it.orderId]!! }
        return result
    }

    private fun findOrderItemMap(orderIds: List<Long>) = em.createQuery(
        "select new com.wkingdeveloper.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id,i.name,oi.orderPrice,oi.count)" +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.order.id in :orderIds", OrderItemQueryDto::class.java
    )
        .setParameter("orderIds", orderIds)
        .resultList.groupBy { it.orderId }

    fun findAllByDto_flat(): List<OrderFlatDto> {
        return em.createQuery(
            "select new com.wkingdeveloper.jpashop.repository.order.query.OrderFlatDto" +
                    "(o.id, m.name, o.orderDate,o.status,d.address,i.name,oi.orderPrice,oi.count)" +
                    " from Order o" +
                    " join o.member m" +
                    " join o.delivery d" +
                    " join o.orderItems oi" +
                    " join oi.item i", OrderFlatDto::class.java
        ).resultList
    }
}
