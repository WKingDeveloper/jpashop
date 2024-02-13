package com.wkingdeveloper.jpashop.repository

//import com.querydsl.core.types.dsl.BooleanExpression
//import com.querydsl.jpa.impl.JPAQueryFactory
import com.wkingdeveloper.jpashop.domain.*
import com.wkingdeveloper.jpashop.domain.Order
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.*
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

//    fun findAll(orderSearch: OrderSearch): List<Order> {
//        return em.createQuery(
//            "select o from Order o join o.member m"
//                    + " where o.status = :status" +
//                    " and m.name like :name",
//            Order::class.java
//        ).setParameter("status", orderSearch.orderStatus)
//            .setParameter("name", orderSearch.memberName)
//            .setMaxResults(1000)
//            .resultList
//    }

//    /**
//     * Todo : 강의 영상에서는 주문목록을 들어가고 검색을 안눌러도 자동으로 먼저 표시되는데 이유가 뭔지
//     */
//    fun findAll(orderSearch: OrderSearch): List<Order> {
//        val order = QOrder.order
//        val member = QMember.member
//
//        val query = JPAQueryFactory(em)
//        return query.select(order)
//            .from(order)
//            .join(order.member, member)
//            .where(statusEq(orderSearch.orderStatus), nameLike(orderSearch.memberName))
//            .limit(1000)
//            .fetch()
//    }
//
//    private fun nameLike(memberName: String?): BooleanExpression? {
//        if (!StringUtils.hasText(memberName)) {
//            null
//        }
//        return QMember.member.name.like("%${memberName}%")
//    }
//
//    private fun statusEq(statusCond: OrderState?): BooleanExpression? {
//        if (statusCond == null) {
//            return null
//        }
//        return QOrder.order.status.eq(statusCond)
//    }

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

    fun findAllWithMemberDelivery(offset: Int, limit: Int): List<Order> {
        return em.createQuery(
            "select o from Order o" +
                    " join fetch o.member m" +
                    " join fetch o.delivery d", Order::class.java
        ).setFirstResult(offset).setMaxResults(limit).resultList
    }


    /**
     * hibernate 6.0부터는 distinct가 자동으로 적용되어 db 레벨에서는 중복된 데이터가 표시되나
     * JPA에서는 중복된 데이터가 합쳐져서 보임 (뻥튀기가 되지 않음)
     * https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#hql-distinct
     */
    fun findAllWithItem(): List<Order> {
        return em.createQuery(
            "select o from Order o " +
                    "join fetch o.member m " +
                    "join fetch o.delivery d " +
                    "join fetch o.orderItems oi " +
                    "join fetch oi.item i", Order::class.java
        ).resultList
    }

}
