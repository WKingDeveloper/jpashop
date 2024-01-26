package com.wkingdeveloper.jpashop.api

import com.wkingdeveloper.jpashop.domain.Member
import com.wkingdeveloper.jpashop.serivce.MemberService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class MemberApiController(
    private val memberService: MemberService
) {

    @GetMapping("/api/v1/members")
    fun membersV1(): List<Member> {
        return memberService.findMembers()
    }

    @GetMapping("/api/v2/members")
    fun membersV2(): Result {
        val findMembers = memberService.findMembers()
        val collect = findMembers.map { MemberDto(it.name) }
        return Result(count = collect.size, data = collect)
    }

    data class Result(
        val count: Int,
        val data: Any
    )

    data class MemberDto(
        val name: String = ""
    )

    @PostMapping("/api/v1/members")
    fun saveMemberV1(@RequestBody @Valid member: Member): CreateMemberResponse {
        val id = memberService.join(member)
        return CreateMemberResponse(id)
    }

    @PostMapping("/api/v2/members")
    fun saveMemberV2(@RequestBody @Valid request: CreateMemberRequest): CreateMemberResponse {
        val id = memberService.join(
            Member(
                name = request.name
            )
        )
        return CreateMemberResponse(id)
    }

    @PutMapping("/api/v2/members/{id}")
    fun updateMemberV2(@PathVariable id: Long, @RequestBody @Valid request: UpdateMemberRequest): UpdateMemberResponse {

        memberService.update(id, request.name)
        val findMember = memberService.findOne(id)
        return UpdateMemberResponse(findMember.id, findMember.name)
    }

    data class CreateMemberResponse(
        val id: Long
    )

    data class CreateMemberRequest(
        val name: String = ""
    )

    data class UpdateMemberResponse(
        val id: Long,
        val name: String
    )

    data class UpdateMemberRequest(
        val name: String = ""
    )


}
