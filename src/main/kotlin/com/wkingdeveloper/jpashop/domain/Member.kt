package com.wkingdeveloper.jpashop.domain

import jakarta.persistence.*

@Entity
class Member(

    @Id @GeneratedValue
    @Column(name = "member_id")
    val id: Long = 0,
    val name: String,
    @Embedded
    var address: Address
) {

    @OneToMany(mappedBy = "member")
    val orders: MutableList<Order> = mutableListOf()

}
