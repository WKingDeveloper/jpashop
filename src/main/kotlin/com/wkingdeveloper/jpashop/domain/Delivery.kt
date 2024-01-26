package com.wkingdeveloper.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Delivery(
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long = 0,

    @Embedded
    val address: Address,

    ) {

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    lateinit var order: Order

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus = DeliveryStatus.READY

}
