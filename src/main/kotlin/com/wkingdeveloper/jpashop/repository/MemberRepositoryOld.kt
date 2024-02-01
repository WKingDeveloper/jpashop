package com.wkingdeveloper.jpashop.repository

import com.wkingdeveloper.jpashop.domain.Member
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryOld(
    private val em: EntityManager
) {
    fun save(member: Member) {
        em.persist(member);
    }

    fun findOne(id: Long): Member {
        return em.find(Member::class.java, id)
    }

    fun findAll(): List<Member> {
        return em.createQuery("select m from Member m", Member::class.java)
            .resultList
    }

    fun findByName(name: String): List<Member> {
        return em.createQuery("select m from Member m where m.name = :name", Member::class.java)
            .setParameter("name", name)
            .resultList
    }
}
