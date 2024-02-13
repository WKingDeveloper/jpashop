package com.wkingdeveloper.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Member(

    @Id @GeneratedValue
    @Column(name = "member_id")
    val id: Long = 0,
    var name: String,
    @Embedded
    val address: Address = Address()
) {

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    val orders: MutableList<Order> = mutableListOf()

}
