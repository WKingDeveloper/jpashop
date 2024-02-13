package com.wkingdeveloper.jpashop.repository.order.simplequery

import javax.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class OrderSimpleQueryRepository(
    private val em: EntityManager
) {
    fun findOrderDtos(): List<OrderSimpleQueryDTO> {
        return em.createQuery(
            "select new com.wkingdeveloper.jpashop.repository.order.simplequery.OrderSimpleQueryDTO(o.id, m.name, o.orderDate, o.status, d.address) " +
                    " from Order o" +
                    " join o.member m" +
                    " join o.delivery d", OrderSimpleQueryDTO::class.java
        ).resultList
    }
}
