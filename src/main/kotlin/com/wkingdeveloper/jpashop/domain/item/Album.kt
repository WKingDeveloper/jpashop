package com.wkingdeveloper.jpashop.domain.item

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("A")
class Album(
    id: Long = 0,
    name: String,
    stockQuantity: Int,
    price: Int,
    @Column
    val artist: String,
    @Column
    val etc: String,
) : Item(id, name, price, stockQuantity)
