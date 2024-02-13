package com.wkingdeveloper.jpashop.serivce

import com.wkingdeveloper.jpashop.domain.item.Book
import javax.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ItemUpdateTest @Autowired constructor(
    private val em: EntityManager
) {

    @Test
    fun updateTest() {
        val book = em.find(Book::class.java, 1L)

        // TX
        book.stockQuantity = 30

        // 변경감지 == dirty checking
        // TX commit
    }
}
