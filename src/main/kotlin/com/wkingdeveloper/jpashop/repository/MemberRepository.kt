package com.wkingdeveloper.jpashop.repository

import com.wkingdeveloper.jpashop.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByName(name: String): List<Member>
}
