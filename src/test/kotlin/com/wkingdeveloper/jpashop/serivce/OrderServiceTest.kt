package com.wkingdeveloper.jpashop.serivce

import com.wkingdeveloper.jpashop.domain.Address
import com.wkingdeveloper.jpashop.domain.Member
import com.wkingdeveloper.jpashop.domain.OrderState
import com.wkingdeveloper.jpashop.domain.item.Book
import com.wkingdeveloper.jpashop.domain.item.Item
import com.wkingdeveloper.jpashop.exception.NotEnoughStockException
import com.wkingdeveloper.jpashop.repository.OrderRepository
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class OrderServiceTest @Autowired constructor(
    private val em: EntityManager,
    private val orderService: OrderService,
    private val orderRepository: OrderRepository
) {

    @Test
    fun 상품주문() {
        val member = createMember()
        em.persist(member)

        val book: Item = createBook()
        em.persist(book)

        val orderCount = 2
        val orderId = orderService.order(member.id, book.id, orderCount)

        val getOrder = orderRepository.findOne(orderId)

        assertEquals(OrderState.ORDER, getOrder.status, "상품 주문시 상태는 ORDER")
        assertEquals(1, getOrder.orderItems.size, "주문한 상품 종류 수가 정확해야 한다.")
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.")
        assertEquals(8, book.stockQuantity, "주문 수량 만큼 재고가 줄어야한다.")

    }


    @Test
    fun 상품주문_재고수량초과() {

        val member = createMember()
        em.persist(member)

        val book: Item = createBook()
        em.persist(book)

        val orderCount = 11
        assertThrows<NotEnoughStockException> {
            val orderId = orderService.order(member.id, book.id, orderCount)
        }
    }

    @Test
    fun 주문취소() {
        val member = createMember()
        em.persist(member)

        val book: Item = createBook()
        em.persist(book)

        val orderCount = 2
        val orderId = orderService.order(member.id, book.id, orderCount)


        orderService.cancelOrder(orderId)

        val getOrder = orderRepository.findOne(orderId)
        assertEquals(OrderState.CANCEL, getOrder.status, "주문 취소시 상태는 CANCEL")
        assertEquals(10, book.stockQuantity, "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.")
    }

    private fun createBook() = Book(
        name = "시골 JPA",
        price = 10000,
        stockQuantity = 10,
        author = "Jin",
        isbn = "qjqj9"
    )

    private fun createMember() =
        Member(name = "kim", address = Address(city = "서울", street = "강가", zipcode = "123-123"))


}
