package com.wkingdeveloper.jpashop.domain.item

import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


@Entity
@DiscriminatorValue("M")
class Movie(
    id: Long = 0,
    name: String,
    stockQuantity: Int,
    price: Int,
    @Column
    val director: String,
    @Column
    val actor: String,
) : Item(id, name, price, stockQuantity)
