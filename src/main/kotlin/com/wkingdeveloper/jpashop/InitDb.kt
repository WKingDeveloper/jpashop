package com.wkingdeveloper.jpashop

import com.wkingdeveloper.jpashop.domain.*
import com.wkingdeveloper.jpashop.domain.item.Book
import javax.persistence.EntityManager
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class InitDb(
    private val initService: InitService
) {
    init {
        initService.dbInit1()
        initService.dbInit2()
    }
}

@Component
@Transactional
class InitService(
    private val em: EntityManager
) {
    fun dbInit1() {
        val member = Member(name = "userA", address = Address("서울", "1", "1111"))
        em.persist(member)

        val book1 = Book(name = "JPA1 BOOK", price = 10000, stockQuantity = 100, author = "js", isbn = "asd")
        em.persist(book1)

        val book2 = Book(name = "JPA2 BOOK", price = 10000, stockQuantity = 100, author = "js2", isbn = "bcs")
        em.persist(book2)

        val orderItem1 = OrderItem(item = book1, orderPrice = 10000, count = 1)
        val orderItem2 = OrderItem(item = book2, orderPrice = 10000, count = 2)

        val delivery = Delivery(address = member.address)
        val order = Order(
            member = member,
            delivery = delivery,
            orderItems = listOf(orderItem1, orderItem2)
        )

        em.persist(order)
    }

    fun dbInit2() {
        val member = Member(name = "userB", address = Address("부산", "2", "2222"))
        em.persist(member)

        val book1 = Book(name = "Spring BOOK", price = 20000, stockQuantity = 200, author = "kk", isbn = "qwe")
        em.persist(book1)

        val book2 = Book(name = "Spring2 BOOK", price = 40000, stockQuantity = 300, author = "kk2", isbn = "zxc")
        em.persist(book2)

        val orderItem1 = OrderItem(item = book1, orderPrice = 20000, count = 3)
        val orderItem2 = OrderItem(item = book2, orderPrice = 40000, count = 4)

        val delivery = Delivery(address = member.address)
        val order = Order(
            member = member,
            delivery = delivery,
            orderItems = listOf(orderItem1, orderItem2)
        )

        em.persist(order)
    }
}
