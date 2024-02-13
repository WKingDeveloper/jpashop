package com.wkingdeveloper.jpashop.domain.item

import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("B")
class Book(
    id: Long = 0,
    name: String,
    stockQuantity: Int,
    price: Int,
    @Column
    val author: String,
    @Column
    val isbn: String,
) : Item(id, name, price, stockQuantity) {

}
