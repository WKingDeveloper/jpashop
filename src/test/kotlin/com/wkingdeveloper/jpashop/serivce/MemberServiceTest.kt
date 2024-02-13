package com.wkingdeveloper.jpashop.serivce

import com.wkingdeveloper.jpashop.domain.Member
import com.wkingdeveloper.jpashop.repository.MemberRepository
import javax.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MemberServiceTest @Autowired constructor(
    private val memberService: MemberService,
    private val memberRepository: MemberRepository,
    private val em: EntityManager
) {

    @Test
    fun 회원가입() {
        val member = Member(
            name = "kim"
        )
        val saveId = memberService.join(member)
        em.flush()
        assertEquals(member, memberRepository.findById(saveId).get())
    }

    @Test()
    fun 중복_회원_예외() {
        val member1 = Member(name = "kim")
        val member2 = Member(name = "kim")

        memberService.join(member1)
        assertThrows<IllegalStateException> {
            memberService.join(member2)
        }
    }
}
