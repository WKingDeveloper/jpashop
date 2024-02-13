package com.wkingdeveloper.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

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
    var order: Order? = null

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus = DeliveryStatus.READY

}
