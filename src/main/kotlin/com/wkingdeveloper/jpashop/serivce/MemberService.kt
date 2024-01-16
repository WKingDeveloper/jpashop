package com.wkingdeveloper.jpashop.serivce

import com.wkingdeveloper.jpashop.domain.Member
import com.wkingdeveloper.jpashop.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository
) {

    // 회원 가입
    @Transactional
    fun join(member: Member): Long {
        validateDuplicateMember(member) // 중복 회원 검증
        memberRepository.save(member)
        return member.id
    }

    // 회원 전체 조회
    fun findMembers(): List<Member> {
        return memberRepository.findAll()
    }

    // 회원 단건 조회
    fun findOne(memberId: Long): Member {
        return memberRepository.findOne(memberId)
    }

    private fun validateDuplicateMember(member: Member) {
        val findMembers = memberRepository.findByName(member.name)
        if (findMembers.isNotEmpty()) {
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }
}
