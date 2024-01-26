package com.wkingdeveloper.jpashop.repository

import com.wkingdeveloper.jpashop.domain.Member
import com.wkingdeveloper.jpashop.domain.Order
import com.wkingdeveloper.jpashop.domain.OrderState
import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import jakarta.persistence.criteria.*
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils


@Repository
class OrderRepository(
    private val em: EntityManager
) {

    fun save(order: Order) {
        em.persist(order)
    }

    fun findOne(id: Long): Order {
        return em.find(Order::class.java, id)
    }

    fun findAll(orderSearch: OrderSearch): List<Order> {
        return em.createQuery(
            "select o from Order o join o.member m"
                    + " where o.status = :status" +
                    " and m.name like :name",
            Order::class.java
        ).setParameter("status", orderSearch.orderStatus)
            .setParameter("name", orderSearch.memberName)
            .setMaxResults(1000)
            .resultList
    }

    fun findAllByString(orderSearch: OrderSearch): List<Order> {
        //language=JPAQL
        var jpql = "select o From Order o join o.member m"
        var isFirstCondition = true
        //주문 상태 검색
        if (orderSearch.orderStatus != null) {
            if (isFirstCondition) {
                jpql += " where"
                isFirstCondition = false
            } else {
                jpql += " and"
            }
            jpql += " o.status = :status"
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.memberName)) {
            if (isFirstCondition) {
                jpql += " where"
                isFirstCondition = false
            } else {
                jpql += " and"
            }
            jpql += " m.name like :name"
        }
        var query = em.createQuery(jpql, Order::class.java).setMaxResults(1000) //최대 1000건
        if (orderSearch.orderStatus != null) {
            query = query.setParameter("status", orderSearch.orderStatus)
        }
        if (StringUtils.hasText(orderSearch.memberName)) {
            query = query.setParameter("name", orderSearch.memberName)
        }
        return query.resultList
    }

    fun findAllByCriteria(orderSearch: OrderSearch): List<Order> {
        val cb: CriteriaBuilder = em.criteriaBuilder
        val cq: CriteriaQuery<Order> = cb.createQuery(Order::class.java)
        val o: Root<Order> = cq.from(Order::class.java)
        val m: Join<Order, Member> = o.join("member", JoinType.INNER) // 회원과 조인

        val criteria = mutableListOf<Predicate>()

        // 주문 상태 검색
        orderSearch.orderStatus?.let {
            val status = cb.equal(o.get<OrderState>("status"), it)
            criteria.add(status)
        }
        if (orderSearch.memberName?.isNotEmpty() == true) {
            val name = cb.like(m.get<String>("name"), "%${orderSearch.memberName}%")
            criteria.add(name)
        }

        cq.where(cb.and(*criteria.toTypedArray()))
        val query: TypedQuery<Order> = em.createQuery(cq).setMaxResults(1000) // 최대 1000건
        return query.resultList
    }

    fun findAllWithMemberDelivery(): List<Order> {
        return em.createQuery(
            "select o from Order o" +
                    " join fetch o.member m" +
                    " join fetch o.delivery d", Order::class.java
        ).resultList
    }

}
