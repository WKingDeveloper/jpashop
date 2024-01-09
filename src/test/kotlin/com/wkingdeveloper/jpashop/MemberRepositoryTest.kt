package com.wkingdeveloper.jpashop

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class MemberRepositoryTest @Autowired constructor(
    val memberRepository: MemberRepository,
    val em: EntityManager
) {

    @Test
    @Transactional
    @Rollback(false)
    fun testMember() {
        val member = Member(username = "memberA")

        val saveId = memberRepository.save(member)
        em.flush()
        em.clear()
        val findMember = memberRepository.find(saveId)


        assertEquals(findMember.id, member.id)
        assertEquals(findMember.username, member.username)
        assertNotEquals(findMember, member)
        println("findMember =  ${findMember}")
        println("member =  ${member}")

    }
}
