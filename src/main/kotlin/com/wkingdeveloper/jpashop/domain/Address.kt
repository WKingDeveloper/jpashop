package com.wkingdeveloper.jpashop.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Address(
    @Column(nullable = false)
    val city: String,
    @Column(nullable = false)
    val street: String,
    @Column(nullable = false)
    val zipcode: String,
)
