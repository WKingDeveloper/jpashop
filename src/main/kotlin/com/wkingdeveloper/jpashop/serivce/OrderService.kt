package com.wkingdeveloper.jpashop.serivce

import com.wkingdeveloper.jpashop.domain.Delivery
import com.wkingdeveloper.jpashop.domain.Order
import com.wkingdeveloper.jpashop.domain.OrderItem
import com.wkingdeveloper.jpashop.repository.ItemRepository
import com.wkingdeveloper.jpashop.repository.MemberRepository
import com.wkingdeveloper.jpashop.repository.OrderRepository
import com.wkingdeveloper.jpashop.repository.OrderSearch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService(
    private val orderRepository: OrderRepository,
    private val memberRepository: MemberRepository,
    private val itemRepository: ItemRepository
) {

    /**
     * 주문
     */
    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long {

        // 엔티티 조회
        val member = memberRepository.findById(memberId).get()
        val item = itemRepository.findOne(itemId)

        // 배송정보 생성
        val delivery = Delivery(address = member.address)

        // 주문 상품 생성
        val orderItem = OrderItem(
            orderPrice = item.price,
            count = count,
            item = item
        )

        // 주문 생성
        val order = Order(
            member = member,
            delivery = delivery,
            orderItems = listOf(orderItem)
        )

        // 주문 저장
        orderRepository.save(order)

        return order.id
    }

    /**
     * 주문 취소
     */
    @Transactional
    fun cancelOrder(orderId: Long) {
        // 주문 엔티티 조회
        val order = orderRepository.findOne(orderId)
        // 주문 취소
        order.cancel()
    }


    //검색
    fun findOrders(orderSearch: OrderSearch): List<Order> {
        return orderRepository.findAll(orderSearch)
    }
}
